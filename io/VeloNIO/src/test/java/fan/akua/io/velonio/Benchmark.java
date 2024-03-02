package fan.akua.io.velonio;


import org.junit.Test;

import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Benchmark {
    public static byte[] fileReadFileBytes(File file) throws IOException {
        FileInputStream fis = null;
        byte[] fileBytes = null;

        try {
            fis = new FileInputStream(file);
            fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return fileBytes;
    }

    public static byte[] okioReadFileBytes(File file) throws IOException {
        BufferedSource source = null;
        byte[] fileBytes = null;

        try {
            source = Okio.buffer(Okio.source(file));
            fileBytes = source.readByteArray();
        } finally {
            if (source != null) {
                source.close();
            }
        }

        return fileBytes;
    }

    @Test
    public void benchmark() {
        // dd if=/dev/zero of=./file_512 bs=1M count=512
        Arrays.asList(
                "/home/tmp/file_2",
                "/home/tmp/file_8",
                "/home/tmp/file_16",
                "/home/tmp/file_32",
                "/home/tmp/file_64",
                "/home/tmp/file_128",
                "/home/tmp/file_256",
                "/home/tmp/file_512"
        ).forEach(Benchmark::benchmark);
    }

    private static void benchmark(String path) {
        System.out.println("file " + path);
        long s, t;
        byte[] bytes;
        try {
            s = System.currentTimeMillis();
            bytes = okioReadFileBytes(new File(path));
            t = System.currentTimeMillis() - s;
            System.out.printf("Benchmark Okio \t\t readBytes(%d): %d \n", bytes.length, t);

            s = System.currentTimeMillis();
            bytes = VeloNIO.readBytes(new File(path));
            t = System.currentTimeMillis() - s;
            System.out.printf("Benchmark VeloNIO \t readBytes(%d): %d \n", bytes.length, t);

            s = System.currentTimeMillis();
            bytes = fileReadFileBytes(new File(path));
            t = System.currentTimeMillis() - s;
            System.out.printf("Benchmark File \t\t readBytes(%d): %d \n", bytes.length, t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
[akua@akua-nix:~]$ neofetch
          ▗▄▄▄       ▗▄▄▄▄    ▄▄▄▖            simon@simon-nix
          ▜███▙       ▜███▙  ▟███▛            ---------------
           ▜███▙       ▜███▙▟███▛             OS: NixOS 24.05.20231227.cfc3698 (Uakari) x86_64
            ▜███▙       ▜██████▛              Host: Notebook NH5x_NH7x_HHx_HJx_HKx
     ▟█████████████████▙ ▜████▛     ▟▙        Kernel: 6.1.69
    ▟███████████████████▙ ▜███▙    ▟██▙       Uptime: 14 hours, 9 mins
           ▄▄▄▄▖           ▜███▙  ▟███▛       Packages: 2090 (nix-system)
          ▟███▛             ▜██▛ ▟███▛        Shell: bash 5.2.21
         ▟███▛               ▜▛ ▟███▛         Resolution: 1920x1080, 1920x1080
▟███████████▛                  ▟██████████▙   DE: GNOME 45.2 (Wayland)
▜██████████▛                  ▟███████████▛   WM: Mutter
      ▟███▛ ▟▙               ▟███▛            WM Theme: Adwaita
     ▟███▛ ▟██▙             ▟███▛             Theme: Adwaita [GTK2/3]
    ▟███▛  ▜███▙           ▝▀▀▀▀              Icons: Adwaita [GTK2/3]
    ▜██▛    ▜███▙ ▜██████████████████▛        Terminal: kgx
     ▜▛     ▟████▙ ▜████████████████▛         CPU: 11th Gen Intel i5-11400H (12) @ 4.500GHz
           ▟██████▙       ▜███▙               GPU: Intel TigerLake-H GT1 [UHD Graphics]
          ▟███▛▜███▙       ▜███▙              GPU: NVIDIA GeForce RTX 3050 Ti Mobile
         ▟███▛  ▜███▙       ▜███▙             Memory: 9036MiB / 15788MiB
         ▝▀▀▀    ▀▀▀▀▘       ▀▀▀▘




 */
/*
file /home/tmp/file_2
Benchmark Okio 		 readBytes(2097152): 50
Benchmark VeloNIO 	 readBytes(2097152): 5
Benchmark File 		 readBytes(2097152): 2

file /home/tmp/file_8
Benchmark Okio 		 readBytes(8388608): 11
Benchmark VeloNIO 	 readBytes(8388608): 4
Benchmark File 		 readBytes(8388608): 6

file /home/tmp/file_16
Benchmark Okio 		 readBytes(16777216): 12
Benchmark VeloNIO 	 readBytes(16777216): 6
Benchmark File 		 readBytes(16777216): 14

file /home/tmp/file_32
Benchmark Okio 		 readBytes(33554432): 19
Benchmark VeloNIO 	 readBytes(33554432): 16
Benchmark File 		 readBytes(33554432): 17

file /home/tmp/file_64
Benchmark Okio 		 readBytes(67108864): 43
Benchmark VeloNIO 	 readBytes(67108864): 33
Benchmark File 		 readBytes(67108864): 35

file /home/tmp/file_128
Benchmark Okio 		 readBytes(134217728): 86
Benchmark VeloNIO 	 readBytes(134217728): 44
Benchmark File 		 readBytes(134217728): 82

file /home/tmp/file_256
Benchmark Okio 		 readBytes(268435456): 279
Benchmark VeloNIO 	 readBytes(268435456): 112
Benchmark File 		 readBytes(268435456): 133

file /home/tmp/file_512
Benchmark Okio 		 readBytes(536870912): 1042
Benchmark VeloNIO 	 readBytes(536870912): 948
Benchmark File 		 readBytes(536870912): 1361
 */