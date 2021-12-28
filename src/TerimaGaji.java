public class TerimaGaji extends Gaji implements PTABC {
    public String namad;
    public static String namab;
    public String j;
    @Override
    public String NoPegawai(int noPegawai) {
        return j;
    }
    @Override
    public String NamaPegawai(String namad, String namab) {
        System.out.println(namad.toUpperCase()+" ".concat(namab).toUpperCase());
        return namab;
    }
    @Override
    public int GajiPokok(int jabatan) {
        
        if (jabatan==1){
            gaji=30000000;
        }
        else if (jabatan==2){
            gaji=20000000;
        }
        else if (jabatan==3){
            gaji=10000000;
        }
        else if (jabatan==4){
            gaji=5000000;
        }
        return gaji;
    }
    @Override
    public int Potongan(int gaji, int jumlahHari) {
        potongan= ((30-jumlahHari)*100000);
        return potongan;
    }
    @Override
    public int TotalGaji(int gaji, int potongan) {
        int totalGaji= gaji-potongan;
        return totalGaji;
        
    }
    @Override
    public String Jabatan(int i) {
        
        if (i==1){
           // String j=Integer.toString(i);
            j="Direktur";
        }
        else if (i==2){
            j="Manager";
        }  
        else if (i==3){
            j="Supervisor";
        }  
        else if (i==4){
            j="Staff";
        }
        return j;      
    }
    @Override
    public int JumlahHariMasuk(int jumlahHari) {
        return jumlahHari;
    }   
}