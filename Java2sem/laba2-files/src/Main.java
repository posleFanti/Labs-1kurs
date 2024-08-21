import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static Path getPath() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите путь к файлу/каталогу: ");
        return Paths.get(in.nextLine());
    }

    private static void printMenu() {
        System.out.println("1. Вывести информацию о файле/каталоге");
        System.out.println("2. Изменение имени файла/каталога");
        System.out.println("3. Создание нового файла или каталога по заданному пути");
        System.out.println("4. Создание копии файла по заданному пути");
        System.out.println("5. Вывод списка файлов каталога");
        System.out.println("6. Вывод списка файлов каталога, имеющих определенное расширение");
        System.out.println("7. Удаление файла или каталога");
        System.out.println("8. Поиск файла или каталога в выбранном каталоге");
        System.out.println("9. Выход из программы");
        System.out.print("> ");
    }

    private static void printFileInfo(Path path) {
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            FileStore store = Files.getFileStore(path);
            System.out.println("Name: " + path.getFileName());
            System.out.println("Path: " + path);
            System.out.println("Absolute path: " + path.toAbsolutePath());
            System.out.println("Parent: " + path.toAbsolutePath().getParent());
            System.out.println("File " + (Files.exists(path) ? "exists" : "not exists"));
            System.out.println("Path " + (path.isAbsolute() ? "absolute" : "not absolute"));
            System.out.println(Files.isReadable(path) ? "Readable" : "Not Readable");
            System.out.println(Files.isWritable(path) ? "Writable" : "Not Writable");
            System.out.println(Files.isExecutable(path) ? "Executable" : "Not Executable");
            System.out.println(attr.isDirectory() ? "Directory" : "Not directory");
            System.out.println(attr.isRegularFile() ? "File" : "Not File");
            System.out.println(attr.isOther() ? "Other" : "Not other");
            System.out.println(attr.isSymbolicLink() ? "Symlink" : "Not symlink");
            System.out.println(Files.isHidden(path) ? "Hidden" : "Not Hidden");
            System.out.println("Last access: " + attr.lastAccessTime());
            System.out.println("Modified: " + attr.lastModifiedTime());
            System.out.println("Creation Time: " + attr.creationTime() + " bytes");
            System.out.println("Size: " + attr.size() + " bytes");
            System.out.println("Usable space: " + store.getUsableSpace() + " bytes");
            System.out.println("Total space: " + store.getTotalSpace() + " bytes");
            System.out.println("Unallocated space: " + store.getUnallocatedSpace() + " bytes");
            System.out.println("Block size: " + store.getBlockSize() + " bytes");
            System.out.println("File system: " + store.type());
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    private static void changeName(Path path) {
        if (!Files.exists(path)){
            System.out.println(path + " не существует!");
            return;
        }
        Scanner in = new Scanner(System.in);
        System.out.print("Введите новое имя файла: ");
        try {
            String newName = in.nextLine();
            Path source = path.toAbsolutePath();
            Files.move(source, source.resolveSibling(newName));
            System.out.println("Файл успешно изменен");
        } catch (Exception e) {
            System.out.println("Файл не был изменен: " + e);
        }
    }

    private static void createFile(Path path) {
        Scanner in = new Scanner(System.in);
        System.out.println("1. Создать каталог");
        System.out.println("2. Создать файл");
        int ans = in.nextInt();
        switch (ans) {
            case 1 -> {
                try {
                    Files.createDirectories(path);
                    System.out.println("Каталог успешно создан");
                } catch (Exception e) {
                    System.out.println("Каталог не был создан: " + e);
                }
            }
            case 2 -> {
                try {
                    if (path.getParent() != null)
                        Files.createDirectories(path.getParent());
                    Files.createFile(path);
                    System.out.println("Файл успешно создан");
                } catch (Exception e) {
                    System.out.println("Файл не был создан: " + e);
                }
            }
            default -> System.out.println("Неверная команда!\nФайл/каталог не будет создан");
        }
    }

    private static void copyFile(Path from, Path to) {
        if (!Files.exists(from)) {
            System.out.println(from + " не существует!");
            return;
        }
        if (!Files.exists(to)) {
            try {
                Files.createDirectories(to);
            } catch (Exception e) {
                System.out.println("Произошла ошибка при создании каталогов на пути к файлу: " + e);
            }
        }
        try {
            Files.copy(from, to.resolve(from.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл успешно скопирован");
        } catch (Exception e) {
            System.out.println("Файл не был скопирован: " + e);
        }
    }

    private static void listFiles(Path path) throws IOException {
        if (!Files.isDirectory(path) || !Files.exists(path)) {
            System.out.println(path + " is not Directory or not exists!\n");
            return;
        }
        List<Path> listPaths;
        try (Stream<Path> paths = Files.walk(path, 1)) {
            listPaths = paths.collect(Collectors.toList());
        }
        for (Path p : listPaths) {
            System.out.println(p.getFileName());
        }
    }

    private static void listFilesWithExt(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            System.out.println(path + " is not Directory or not exists!\n");
            return;
        }
        List<Path> listPaths;
        Scanner in = new Scanner(System.in);
        System.out.print("Введите расширение: ");
        String ext = in.nextLine();
        try (Stream<Path> paths = Files.walk(path, 1)) {
            listPaths = paths
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.endsWith(ext))
                    .map(Path::of)
                    .collect(Collectors.toList());
        }
        for (Path p : listPaths) {
            System.out.println(p.getFileName());
        }
    }

    private static void deleteFile(Path path) throws IOException {
        if (!Files.exists(path))
            throw new IllegalArgumentException("File doesn't exists!");
        if (!Files.isDirectory(path))
            Files.delete(path);
        else {
            List<Path> listPaths;
            try (Stream<Path> paths = Files.walk(path, 1)) {
                listPaths = paths.collect(Collectors.toList());
            }
            if (listPaths.size() == 1) {
                Files.delete(listPaths.get(0));
                return;
            }
            for (int i = listPaths.size() - 1; i >= 0; i--)
                deleteFile(listPaths.get(i));
        }
    }

    private static void findFileInDir(Path file, Path dir) throws IOException {
        if (!Files.isDirectory(dir) || !Files.exists(dir)) {
            System.out.println(dir + " is not Directory or not exists!\n");
            return;
        }
        List<Path> list;
        try (Stream<Path> paths = Files.walk(dir, 1)) {
            list = paths
                    .filter(p -> p.getFileName().equals(file.getFileName()))
                    .collect(Collectors.toList());
        }
        if (list.isEmpty())
            System.out.println("Такого файла нет в каталоге\n");
        else {
            for (Path p : list)
                System.out.println("Путь к найденному файлу: " + p.toAbsolutePath() + "\n");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int ans;
        do {
            printMenu();
            ans = in.nextInt();
            switch (ans) {
                case 1 -> printFileInfo(getPath());
                case 2 -> changeName(getPath());
                case 3 -> createFile(getPath());
                case 4 -> {
                    System.out.println("Путь откуда");
                    Path from = getPath();
                    System.out.println("Путь куда");
                    Path to = getPath();
                    copyFile(from, to);
                }
                case 5 -> listFiles(getPath());
                case 6 -> listFilesWithExt(getPath());
                case 7 -> {
                    try {
                        deleteFile(getPath());
                        System.out.println("Файл(каталог) был удален(с содержимым)");
                    } catch (Exception e) {
                        System.out.println("Произошла ошибка при удалении файла/каталога: " + e);
                    }
                }
                case 8 -> {
                    System.out.println("Название файла который ищем");
                    Path file = getPath();
                    System.out.println("Путь откуда");
                    Path dir = getPath();
                    findFileInDir(file, dir);
                }
                case 9 -> System.out.println("Выход из программы");
                default -> System.out.println("Неверная команда!");
            }
        } while (ans != 9);
    }
}