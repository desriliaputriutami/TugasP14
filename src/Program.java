import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Program {
    static Connection conn;
    public static void main(String[] args) throws Exception {
        LocalDateTime tanggal = LocalDateTime.now();
        DateTimeFormatter formattgl = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss");
        String formattedDate =tanggal.format(formattgl).toLowerCase();
        Scanner input =new Scanner(System.in);
        int pilihan;
        boolean next=true;
        
        String url = "jdbc:mysql://localhost:3306/db_pegawai";
		try {
            while(next){
            clearScreen();
            Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,"root","");
            System.out.println("__________________________________________\n\tClass Driver ditemukan\n.........................................\n");
            System.out.println("==========================================");
            System.out.println("===========  Database Pegawai  ===========");
            System.out.println("Tanggal Waktu: ".toLowerCase() + formattedDate);
            System.out.println("==========================================");
            System.out.println("\t1. Lihat Data Pegawai");
            System.out.println("\t2. Tambah Data Pegawai");
            System.out.println("\t3. Cari Data Pegawai");
            System.out.println("\t4. Ubah Data Pegawai");
            System.out.println("\t5. Hapus Data Pegawai");

            System.out.print("\n\nPilihan Anda :");
            pilihan=input.nextInt();

            switch (pilihan){
                case 1:
                viewData();
                break;
                case 2:
                saveData();
                break;
                case 3:
                searchData();
                break;
                case 4:
                updateData();
                break;
                case 5:
                deleteData();
                break;
                default:
                System.err.println("Inputan anda salah\nSilahkan pilih untuk no 1-5");
                }
                System.out.print("\nApakah Anda ingin melanjutkan?");
                String lanjut=input.next();
                next=lanjut.equalsIgnoreCase("y");
            }
        }
		catch(ClassNotFoundException ex) {
			System.err.println("Driver Error");
			System.exit(0);
		}
		catch(SQLException e){
			System.err.println("Tidak berhasil koneksi");
		}
}
    private static void viewData() throws SQLException {
        clearScreen();
        System.out.println("----------------------------------\n>>>>>> Data Seluruh Pegawai <<<<<<\n----------------------------------");
        String sql ="SELECT * FROM pegawai";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()){
            System.out.print("\nNo.Pegawai\t: ");
            System.out.print(result.getInt("no_pegawai"));
			System.out.print("\nNama\t\t: ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t: ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nGaji Pokok\t: ");
            System.out.print(result.getInt("gaji_pokok"));
            System.out.print("\nJumlah Hari Masuk: ");
            System.out.print(result.getInt("jumlah_hari_masuk"));
            System.out.print("\nPotongan\t: ");
            System.out.print(result.getInt("potongan"));
            System.out.print("\nTotal Gaji\t: ");
            System.out.print(result.getInt("total_gaji"));
            System.out.println("\n_____________________________________");
		}
    }
    private static void saveData() throws SQLException{
        System.out.println("----------------------------------\n>>>>>> Tambah Data Pegawai <<<<<<\n----------------------------------".toUpperCase());
		Scanner input = new Scanner (System.in);				
		try {	
        TerimaGaji pegawai= new TerimaGaji();
        System.out.println("Masukkan Data Anda :");
        System.out.print("Nama Depan :");
        pegawai.namad = input.nextLine();
        System.out.print("Nama Belakang :");
        pegawai.namab = input.nextLine();
        System.out.print("\nNo Pegawai :");
        int no_pegawai = input.nextInt();
        int pilih;
        
        do{
        clearScreen();
        System.out.println("\nNomor Untuk Jenis Jabatan :");
        System.out.println("\t1. Direktur");
        System.out.println("\t2. Manager");
        System.out.println("\t3. Supervisor");
        System.out.println("\t4. Staff");
        System.out.print("\nMasukkan no untuk jabatan anda [1-4] :");
         pilih = input.nextInt();}
        while(pilih<=0||pilih>4);
        
        do{
        clearScreen();
        System.out.println("\nJumlah Hari Masuk [1-30] :");
        pegawai.jumlahHari= input.nextInt();}
        while(pegawai.jumlahHari<=0||pegawai.jumlahHari>30);
		String sql = "INSERT INTO pegawai (no_pegawai, nama, jabatan, gaji_pokok, jumlah_hari_masuk, potongan, total_gaji) VALUES ('"+no_pegawai+"', '"+pegawai.namad.toUpperCase()+" ".concat(pegawai.namab).toUpperCase()+"','"+pegawai.Jabatan(pilih)+"', '"+pegawai.GajiPokok(pilih)+"', '"+pegawai.JumlahHariMasuk(pegawai.jumlahHari)+"', '"+pegawai.Potongan(pegawai.gaji, pegawai.jumlahHari)+"', '"+pegawai.TotalGaji(pegawai.gaji, pegawai.potongan)+"')";
		Statement statement = conn.createStatement();
        statement.execute(sql);
        System.out.println("Input data berhasil dilakukan");
	    } catch (SQLException e) {
	        System.err.println("Terjadi kesalahan input data");
	    } catch (InputMismatchException e) {
	    	System.err.println("Hanya input angka!");
	   	}
    }
    private static void searchData() throws SQLException {
        clearScreen();
        System.out.println("----------------------------------\n>>>>>> Cari Data Pegawai <<<<<<\n----------------------------------".toUpperCase());		
        Scanner inputCari = new Scanner (System.in);	
		System.out.print("Masukkan No Pegawai : ");
		String search = inputCari.nextLine();
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM pegawai WHERE no_pegawai LIKE '%"+search+"%'";
        ResultSet result = statement.executeQuery(sql); 
                
        while(result.next()){
            System.out.print("\nNo.Pegawai\t: ");
            System.out.print(result.getInt("no_pegawai"));
			System.out.print("\nNama\t\t: ");
            System.out.print(result.getString("nama"));
            System.out.print("\nJabatan\t\t: ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nGaji Pokok\t: ");
            System.out.print(result.getInt("gaji_pokok"));
            System.out.print("\nJumlah Hari Masuk: ");
            System.out.print(result.getInt("jumlah_hari_masuk"));
            System.out.print("\nPotongan\t: ");
            System.out.print(result.getInt("potongan"));
            System.out.print("\nTotal Gaji\t: ");
            System.out.print(result.getInt("total_gaji"));
            System.out.println("\n_____________________________________");
        }
    }
    private static void updateData() throws SQLException{
        Scanner input = new Scanner (System.in);
		try {
            clearScreen();
            viewData();
            System.out.println("----------------------------------\n>>>>>> Ubah Data Pegawai <<<<<<\n----------------------------------".toUpperCase());		
            System.out.print("Masukkan No Pegawai dari data yang ingin diubah : ");
            int no_pegawai = input.nextInt();
            String sql = "SELECT * FROM pegawai WHERE no_pegawai = " +no_pegawai;
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if(result.next()){
                TerimaGaji pegawai= new TerimaGaji();
                System.out.print("Nama Depan :");
                pegawai.namad = input.next();
                pegawai.namad+=input.nextLine();
                System.out.print("Nama Belakang :");
                pegawai.namab = input.next();
                pegawai.namab+=input.nextLine();
                int pilih;
                do{
                clearScreen();
                System.out.println("\nNomor Untuk Jenis Jabatan :");
                System.out.println("\t1. Direktur");
                System.out.println("\t2. Manager");
                System.out.println("\t3. Supervisor");
                System.out.println("\t4. Staff");
                System.out.print("\nMasukkan no untuk jabatan anda [1-4] :");
                pilih = input.nextInt();}
                while(pilih<=0||pilih>4);
                do{
                clearScreen();
                System.out.println("\nJumlah Hari Masuk [1-30] :");
                pegawai.jumlahHari= input.nextInt();}
                while(pegawai.jumlahHari<0||pegawai.jumlahHari>30);
                sql = "UPDATE pegawai SET nama='"+pegawai.namad.toUpperCase()+" ".concat(pegawai.namab).toUpperCase()+"',jabatan='"+pegawai.Jabatan(pilih)+"',gaji_pokok='"+pegawai.GajiPokok(pilih)+"',jumlah_hari_masuk= '"+pegawai.JumlahHariMasuk(pegawai.jumlahHari)+"',potongan='"+pegawai.Potongan(pegawai.gaji, pegawai.jumlahHari)+"',total_gaji='"+pegawai.TotalGaji(pegawai.gaji, pegawai.potongan)+"'WHERE no_pegawai='"+no_pegawai+"'";
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Data Pegawai "+no_pegawai+" berhasil diupdate");
                }
            }
            statement.close();        
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam update data!");
            System.err.println(e.getMessage());
        }
    }
    private static void deleteData() {
        clearScreen();
		System.out.println("----------------------------------\n>>>>>> Hapus Data Pegawai <<<<<<\n----------------------------------".toUpperCase());		
		Scanner input = new Scanner (System.in);
		try{
	        clearScreen();
            viewData();
	        System.out.print("Masukkan No Pegawai dari data yang ingin hapus : ");
            int no_pegawai = input.nextInt();
	        String sql = "DELETE FROM pegawai WHERE no_pegawai = "+ no_pegawai;
	        Statement statement = conn.createStatement();
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Data Pegawai dengan  No Pegawai " +no_pegawai+ " berhasil dihapus");
	        }
	   }catch(SQLException e){
	        System.out.println("Terjadi kesalahan");
	        }
    }
    private static void clearScreen(){try{
            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }  
    }catch (Exception ex){
        System.err.println("Tidak Bisa Clearscreen");
    }
    }
}