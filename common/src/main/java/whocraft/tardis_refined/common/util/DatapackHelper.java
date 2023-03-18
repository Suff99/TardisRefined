package whocraft.tardis_refined.common.util;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.bridge.game.PackType;
import com.mojang.serialization.JsonOps;
import net.minecraft.FileUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.AABB;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.mixin.MinecraftServerStorageAccessor;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
public class DatapackHelper {

    public static boolean writeDesktopToFile(ServerLevel level, BlockPos bottomCorner, BlockPos topCorner, boolean ignoreEntities, ResourceLocation structure, DesktopTheme desktop, String datapackName){
        MinecraftServerStorageAccessor accessor = (MinecraftServerStorageAccessor)level.getServer();
        Path rootDir = accessor.getStorageSource().getLevelPath(LevelResource.DATAPACK_DIR).normalize();
        Path datapackRoot = rootDir.resolve(datapackName);
        Path datapackDataFolder = datapackRoot.resolve("data");
        String fileExtension = ".json";
        JsonObject currentDesktop = DesktopTheme.getCodec().encodeStart(JsonOps.INSTANCE, desktop).get()
                .ifRight(right -> {
                    TardisRefined.LOGGER.error(right.message());
                }).orThrow().getAsJsonObject();
        Path output = createAndValidatePathToDatapackObject(datapackDataFolder, desktop.getIdentifier(), TardisDesktops.getReloadListener(), fileExtension);
        createPackDefinition(datapackRoot);
        if (createStructure(level, bottomCorner, topCorner, ignoreEntities, structure, datapackDataFolder)){
            if (saveJsonToPath(currentDesktop, output))
                return true;
        }
        return false;
    }

    public static boolean saveJsonToPath(JsonElement jsonElement, Path path){
        try {
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write(TardisRefined.GSON.toJson(jsonElement));
            }
            return true;
        }
        catch (IOException e) {
            TardisRefined.LOGGER.error(e.getMessage().toString());
            return false;
        }
    }

    public static void createPackDefinition(Path packRoot){
        Path metaFile = packRoot.resolve("pack.mcmeta");
        if (!Files.exists(metaFile)) {
            JsonObject pack = new JsonObject();
            pack.addProperty("pack_format", SharedConstants.getCurrentVersion().getPackVersion(PackType.DATA));
            pack.addProperty("description", "Datapack generated by the Tardis Refined mod");

            JsonObject root = new JsonObject();
            root.add("pack", pack);

            try {
                Files.createDirectories(packRoot);
                try (BufferedWriter writer = Files.newBufferedWriter(metaFile)) {
                    writer.write(TardisRefined.GSON.toJson(root));
                }
            }
            catch (IOException e) {
                TardisRefined.LOGGER.error(e.getMessage().toString());
            }
        }
    }

    public static boolean createStructure(ServerLevel level, BlockPos bottomCorner, BlockPos topCorner, boolean ignoreEntities, ResourceLocation structure, Path packRoot){
        StructureTemplateManager structureTemplateManager = level.getStructureManager();

        StructureTemplate structureTemplate;
        try {
            structureTemplate = structureTemplateManager.getOrCreate(structure);
        } catch (ResourceLocationException e) {
            TardisRefined.LOGGER.error(e.getMessage().toString());
            return false;
        }

        AABB boundingBox = new AABB(bottomCorner, topCorner);

        ////===Size Calculation Start===
        // This mimics using the Structure Block's Detect Size feature with CORNER mode, where the start and end corners need to be placed one block diagonally outside the structure area.

        //Minus one so we get the right size dimensions. If we didn't the size will be oversize by one on all sides, when we call StructureTemplate#fillFromWorld
        int xSize = (int)boundingBox.getXsize() - 1;
        int ySize = (int)boundingBox.getYsize() - 1;
        int zSize = (int)boundingBox.getZsize() - 1;
        Vec3i structureSize = new Vec3i(xSize, ySize, zSize); //1.19.3+ - use joml maths version

        //Add one to each dimension to move the bottom corner position one block inwards, diagonally and upwards.
        BlockPos pasteStartPos = bottomCorner.offset(1,1,1);

        //===Size Calculation End===

        structureTemplate.fillFromWorld(level, pasteStartPos, structureSize, ignoreEntities, Blocks.STRUCTURE_VOID);
        structureTemplate.setAuthor("");

        Path path = createAndValidatePathToDatapackObject(packRoot, structure, "structures", ".nbt");
        Path pathParent = path.getParent();
        if (pathParent == null) {
            return false;
        } else {
            try {
                Files.createDirectories(Files.exists(pathParent, new LinkOption[0]) ? pathParent.toRealPath() : pathParent);
            } catch (IOException var13) {
                TardisRefined.LOGGER.error("Failed to create parent directory: {}", pathParent);
                return false;
            }

            CompoundTag compoundTag = structureTemplate.save(new CompoundTag());

            try {
                OutputStream outputStream = new FileOutputStream(path.toFile());

                try {
                    NbtIo.writeCompressed(compoundTag, outputStream);
                } catch (Throwable throwable) { //Catch nbt write errors
                    try {
                        outputStream.close();
                    } catch (Throwable throwable1) { //Catch output stream close errors
                        throwable.addSuppressed(throwable1);
                    }

                    throw throwable;
                }

                outputStream.close();
                return true;
            } catch (Throwable throwable) {
                return false;
            }
        }

    }

    private static Path createAndValidatePathToDatapackObject(Path path, ResourceLocation resourceLocation, CodecJsonReloadListener<?> listener, String fileExtension) {
        return createAndValidatePathToDatapackObject(path, resourceLocation, listener.getFolderName(), fileExtension);
    }

    private static Path createAndValidatePathToDatapackObject(Path path, ResourceLocation resourceLocation, String folderName, String fileExtension) {
        if (resourceLocation.getPath().contains("//")) {
            throw new ResourceLocationException("Invalid resource path: " + resourceLocation);
        } else {
            Path path2 = createPathToResult(path, resourceLocation, folderName, fileExtension);
            if (path2.startsWith(path) && FileUtil.isPathNormalized(path2) && FileUtil.isPathPortable(path2)) {
                return path2;
            } else {
                throw new ResourceLocationException("Invalid resource path: " + path2);
            }
        }
    }

    public static Path createPathToResult(Path path, ResourceLocation resourceLocation, String folderName, String fileExtension) {
        try {
            Path datapackRoot = path.resolve(resourceLocation.getNamespace());
            Path folder = datapackRoot.resolve(folderName);
            return FileUtil.createPathToResource(folder, resourceLocation.getPath(), fileExtension);
        } catch (InvalidPathException e) {
            throw new ResourceLocationException("Invalid resource path: " + resourceLocation, e);
        }
    }
}
