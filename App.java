package Database;

import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;

public class App {
    static Connection con;
    static Date date = new Date();
       
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in) ;
        String pilihan;
        boolean isLanjutkan = true;

        Transaksi transaksi = new Transaksi();
        

        String url = "jdbc:mysql://localhost:3306/programhotel";
        String username = "root";
        String password = "";

        System.out.println("\n");
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.display();
        System.out.println("\n");

        //exception
        //koneksi database
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Class Driver ditemukan");
        }

        catch(ClassNotFoundException ex) {
            System.err.println("Driver Error");
            System.exit(0);
            }
                
        catch(SQLException e){
            System.out.println("Tidak berhasil koneksi");
            }


 //=======================================================================================================================           

        System.out.println("+===========================================+");
            System.out.println("+-------------------------------------------+");
            System.out.println("|              SELAMAT DATANG DI            |");
            System.out.println("|               FIRA HOTEL                  |");
            System.out.println("+-------------------------------------------+");
            System.out.println("|        "+date+"       |");
            System.out.println("+===========================================+");

        //percabangan
        do{
            System.out.println("+-------------------------------------------+");
            System.out.println("|     Silahkan Pilih Menu di bawah ini      |");
            System.out.println("+-------------------------------------------+");
            System.out.println("|  [1] Pemesanan Kamar Hotel                |");
            System.out.println("|  [2] Tampilkan Data Pemesanan             |");
            System.out.println("|  [3] Ubah Data Pemesanan                  |");
            System.out.println("|  [4] Hapus Data Pemesanan                 |");
            System.out.println("|  [5] Cari Data Pemesanan                  |");
            System.out.println("|  [6] Kosongkan Data Pemesanan             |");
            System.out.println("|  [7] Keluar                               |");
            System.out.println("+-------------------------------------------+");

            System.out.print("Masukkan Pilihan Anda : ");
            pilihan = input.next();

            //percabangan
            switch (pilihan){
                case "1" :
                    //collection : hashmap
                    HashMap <Integer, String> cul = new HashMap <Integer, String>();
                    cul.put(1000000, "Standard Room");
                    cul.put(1500000, "Superior Room");
                    cul.put(2000000, "Deluxe Room");

                    System.out.println("==================================================================================================");
                    System.out.println("\nDaftar Pilihan Kamar \t : " + cul.entrySet());
                    System.out.println("Ukuran Hashmap \t\t : " + cul.size());
                    System.out.println("==================================================================================================");
                    System.out.println("\n");

                    transaksi.tambahData();

                    break;

                case "2" :
                    transaksi.lihatdata();
                    break;
                case "3" :
                    transaksi.ubahdata(); 
                    break;
                case "4" :
                    transaksi.hapusdata();
                    break;
                case "5" :
                transaksi.caridata();
                    break;
                case "6" :
                    transaksi.resetdata();
                    break;
                case "7" :
                    System.out.println("-------------------------");
                    System.out.println("  PROGRAM TELAH SELESAI  ");
                    System.out.println("-------------------------");
                    break;
                default :
                    System.out.println("Maaf, Pilihan Tidak Tersedia!");
                    break;  
            }

            System.out.print("\nApakah Anda Masih Ingin Lanjut[y/n]?");
                pilihan = input.next();
                isLanjutkan = pilihan.equalsIgnoreCase("y");
            
        }
        while (isLanjutkan);

        System.out.println("\n===========================================");
        System.out.println("|Terima Kasih Telah Mengunjungi Hotel Kami|");
        System.out.println("===========================================");
    }



}
