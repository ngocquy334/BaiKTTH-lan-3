package vn.edu.ute.datphong.model
import java.io.Serializable
data class DonDatPhong(
    val hoten: String,
    val sdt:String,
    val email:String,
    val maLoaiPhong:String,
    val ngayNhan:String,
    val soDem:Int,
    val soKhach: Int,
    val ghichu: String = ""
):Serializable{
    fun tomTat():String{
        return "$hoten - $maLoaiPhong - $ngayNhan ($soDem đêm)"
    }
}
