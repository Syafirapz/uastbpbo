package Database;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

//inheritance
public class Transaksi extends Customer{
    static Connection con;
    public String kamar;
    public Integer pilihKamar;

    String url = "jdbc:mysql://localhost:3306/programhotel";
        String username = "root";
        String password = "";

        Date date = new Date();
        String tanggal = String.format("%tF", date);
        

        @Override
        public void jenisKamar() {
           System.out.print("Inputkan Jenis Kamar       = ");
           pilihKamar = input.nextInt();

        //percabangan
           if(pilihKamar == 1){
               jenisKamar = "Standard Room";
           }

           else if(pilihKamar == 2){
               jenisKamar = "Superior Room";
           }

           else if(pilihKamar == 3){
               jenisKamar = "Deluxe Room";
           }

            
        }

    @Override
    public void hargaKamar() {
        //percabangan
        if(pilihKamar == 1){
            hargaKamar = 1000000;
        }

        else if(pilihKamar == 2){
            hargaKamar = 1500000;
        }

        else if(pilihKamar == 3){
            hargaKamar = 2000000;
        }
       
    }

//==============================================================================================================================================================================================================================================================================
//pengolahan database 
public void tambahData() throws SQLException, ClassNotFoundException {
    //exception
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);

        System.out.println("===============================");
        String text = "=====TAMBAH DATA RESERVASI=====";
        //method string
        System.out.println(text.toUpperCase());
        System.out.println("===============================");

        noPemesanan();
        namaCustomer();
        jenisKamar();
        hargaKamar();
        lamaMenginap();
        System.out.println("\n=====Ringkasan Reservasi=====");
        System.out.println("Jenis Kamar                 = " + jenisKamar);
        System.out.println("Harga Kamar                 = " + hargaKamar);
        totalHarga();
        tanggal();
        waktu();

        
        String sql = "INSERT INTO reservasi (nopemesanan, namacustomer, jeniskamar, hargakamar, lamamenginap, totalharga, tanggaltransaksi) VALUES ('"+noPemesanan+"','"+namaCustomer+"','"+jenisKamar+"','"+hargaKamar+"','"+lamaMenginap+"', '"+totalHarga+"', '"+tanggal+"')";

        Statement statement = con.createStatement();
        statement.execute(sql);
        System.out.println("Data Berhasil Diinputkan!");
    }
    
    catch (SQLException e){
        System.err.println("\nTerjadi kesalahan input data");
    }
    catch (InputMismatchException e) {
        System.err.println("\nInputlah dengan angka saja");
       }
}
//==============================================================================================================================================================================================================================================================================
//pengolahan database 
public void lihatdata() throws SQLException, ClassNotFoundException{
    
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);

        System.out.println("====================================");
        String text = "=====MENAMPILKAN DATA RESERVASI=====";
        System.out.println(text.toUpperCase());
        System.out.println("====================================");

        String sql = "SELECT * FROM reservasi";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

            System.out.println("\n-----------------------------------------------------------------------------------------------------");
            String format1 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
            System.out.printf(format1,"No", "No Pemesanan","Nama Customer","Jenis Kamar","Harga Kamar","Lama Menginap","Total Harga","Tanggal");
            System.out.println("\n-----------------------------------------------------------------------------------------------------");

        int i=1;
        while (result.next()){
            String format2 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
            System.out.printf(format2, i, result.getInt("nopemesanan"),result.getString("namacustomer"),result.getString("jeniskamar"),result.getInt("hargakamar"),result.getInt("lamamenginap"),result.getInt("totalharga"),result.getDate("tanggaltransaksi"));
            System.out.println("\n-----------------------------------------------------------------------------------------------------");

            i++;
        }
}
//==============================================================================================================================================================================================================================================================================
//pengolahan database 
public void ubahdata() throws SQLException, ClassNotFoundException{
    System.out.println("=============================");
    String text = "=====UBAH DATA PEMBELIAN=====";
    System.out.println(text.toUpperCase());
    System.out.println("=============================");
    
    Scanner inputan = new Scanner (System.in);
    
    //exception
    try{
        lihatdata();
    
        System.out.print("Masukkan No Pemesanan  yang Akan diubah : ");
        noPemesanan = Integer.parseInt(inputan.nextLine());

        String sql = "SELECT * FROM reservasi WHERE nopemesanan = " +noPemesanan;

        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        if(result.next()){
            System.out.print("Nama Pelanggan ["+result.getString("namacustomer")+"]\t: ");
            namaCustomer = inputan.next();

            System.out.println("\n");

            sql = "UPDATE reservasi SET namacustomer='"+namaCustomer+"' WHERE nopemesanan='"+noPemesanan+"' ";
    
            if(statement.executeUpdate(sql) > 0){
                System.out.println("Data Berhasil diperbarui!");
            }
        }
        statement.close();
    }

    catch (SQLException e){
        System.err.println("Terjadi kesalahan Ubah data");
        System.err.println(e.getMessage());
        
    }
     
}
//=======================================================================================================================================================================================================================
//pengolahan database 
public void hapusdata() throws SQLException, ClassNotFoundException {
    System.out.println("==============================");
    String text = "=====HAPUS DATA PEMBELIAN=====";
    System.out.println(text.toUpperCase());
    System.out.println("==============================");
    
    Scanner inputan = new Scanner (System.in);

    lihatdata();
    
    //exception
    try{
        
        System.out.print("Ketik nomor pemesanan yang akan dihapus : ");
        noPemesanan = Integer.parseInt(inputan.nextLine());
        
        String sql = "DELETE FROM reservasi WHERE nopemesanan = "+ noPemesanan;
        Statement statement = con.createStatement();
        
        if(statement.executeUpdate(sql) > 0){
            System.out.println("Berhasil menghapus data dengan nomor pemesanan ("+noPemesanan+")");
        }
    }
    catch(SQLException e){
         System.out.println("Terjadi kesalahan dalam menghapus data barang");
         System.err.println(e.getMessage());
         }
    
}
//==========================================================================================================================================================================================================================================================
//pengolahan database 
public void caridata() throws SQLException, ClassNotFoundException {
    System.out.println("=============================");
    String text = "=====Cari Data PEMBELIAN=====";
    System.out.println(text.toUpperCase());
    System.out.println("=============================");

    Class.forName("com.mysql.cj.jdbc.Driver");
    con = DriverManager.getConnection(url, username, password);

    
    Scanner inputan = new Scanner (System.in);
            
    System.out.print("Masukkan Nomor Pemesanan : ");
    
    String keyword = inputan.nextLine();
    Statement statement = con.createStatement();
    String sql = "SELECT * FROM reservasi WHERE nopemesanan LIKE '%"+keyword+"%'";
    ResultSet result = statement.executeQuery(sql);
    
    tanggal();

    //exception
    try{
    System.out.println("\n-----------------------------------------------------------------------------------------------------");
    String format1 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
    System.out.printf(format1,"No", "No Pemesanan","Nama Customer","Jenis Kamar","Harga Kamar","Lama Menginap","Total Harga","Tanggal");
    System.out.println("\n-----------------------------------------------------------------------------------------------------");
     
    int i=1;
    while(result.next()){
        String format2 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
        System.out.printf(format2, i, result.getInt("nopemesanan"),result.getString("namacustomer"),result.getString("jeniskamar"),result.getInt("hargakamar"),result.getInt("lamamenginap"),result.getInt("totalharga"),result.getDate("tanggaltransaksi"));
        System.out.println("\n-----------------------------------------------------------------------------------------------------");

        i++;
    }
    System.out.println("Berhasil mencari data pemesanan"); 
        
    }
    catch(SQLException e){
         System.out.println("Terjadi kesalahan dalam mencari data pemesanan");
         System.err.println(e.getMessage());
         }
    
}

//===========================================================================================================================================================================================================================================================================================================================================================================================
//pengolahan database 
public void resetdata() throws SQLException, ClassNotFoundException {
    System.out.println("==============================");
    String text = "=====RESET DATA PEMBELIAN=====";
    System.out.println(text.toUpperCase());
    System.out.println("==============================");
    
    lihatdata();
    
    //exception
    try{
        
        String sql = "DELETE FROM reservasi";
        Statement statement = con.createStatement();
        
        if(statement.executeUpdate(sql) > 0){
            System.out.println("Berhasil mereset data pemesanan");
        }
    }
    catch(SQLException e){
         System.out.println("Terjadi kesalahan dalam mereset data pemesanan");
         System.err.println(e.getMessage());
         }
    
}
}
