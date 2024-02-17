package com.vuongle.imaginepg.shared.utils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StorageUtils {

    public static String buildDateFilePath() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public static boolean createFile(String filePath) {
        try {
            File file = new File(filePath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            return file.createNewFile();
        } catch (Exception ex) {
            System.out.println("Error saving file " + ex);
        }

        return false;
    }

    public static long createFile(InputStream in, Path imagePath) {
        try {
            if (!Files.exists(imagePath)) {
                Files.createDirectories(imagePath.getParent());
            }
            return Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            System.out.println("Error saving file " + ex);
        }

        return 0;
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        file.deleteOnExit();
    }

    public static String buildPathFromName(String name) {
        List<String> splitByDot = Arrays.asList(name.split("\\."));
        String ext = splitByDot.get(splitByDot.size() - 1);

        String buildedString = "";
        if (splitByDot.size() > 2) {
            List<String> prev = splitByDot.subList(0, splitByDot.size() - 2);
            buildedString = String.join("", prev);
        } else {
            buildedString = splitByDot.get(0);
        }

        return buildFileName(buildedString)  + "." + ext;
    }

    public static String buildPathFromName(String name, String ext) {
        name = StringUtils.removeAccents(name);
        name = StringUtils.preprocessFilePath(name);
        name = name.replace("-", "");
        return buildFileName(name)  + "." + ext;
    }

    private static String buildFileName(String name) {
        List<String> splitData = Arrays.asList(name.split(" "));
        return splitData.stream()
                .filter(item -> !item.equals("-"))
                .filter(item -> !item.isBlank())
                .filter(item -> !item.isEmpty())
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.joining("-"));
    }

}
