package vn.edu.ute.datphong.helper
import vn.edu.ute.datphong.data.DuLieuMau
import vn.edu.ute.datphong.model.DonDatPhong
import vn.edu.ute.datphong.model.LoaiPhong

object DatPhongHelper {
    fun timLoaiPhong(ma: String): LoaiPhong? {
        val maChuan = ma.trim()
        return DuLieuMau.dsLoaiPhong.find { it.ma.equals(maChuan, ignoreCase = true) }
    }
    fun hopLeSoKhach(loai: LoaiPhong, soKhach:Int): Boolean{
        return soKhach in 1..(loai.sucChua +2)
    }

    fun tinhTienPhong_2415053122235(loai: LoaiPhong, soDem: Int, soKhach: Int): Long {
        if (soDem < 1 || !hopLeSoKhach(loai, soKhach)){
            return 0L
    }
    var tienPhong = loai.giaMotDem * soDem
    if(soDem>=4) {
        tienPhong = tienPhong * 85 / 100
    }

    val soKhachVuot = if (soKhach > loai.sucChua) soKhach - loai.sucChua else 0
    val phuThu = soKhachVuot.toLong() * 150000L * soDem

    return tienPhong + phuThu
}
    fun taoMaDon(don:DonDatPhong):String{
    val parts = don.ngayNhan.trim().split("/")
    val ngayThang = if(parts.size>=2) parts[0] + parts[1] else "0000"
    return " DP235-$ngayThang-${don.maLoaiPhong.uppercase()}"
    }
    fun locTheoSoKhach(ds: List<LoaiPhong>, soKhach:Int): List<LoaiPhong>{
        return ds.filter{ it.sucChua>=soKhach}
            .sortedWith(compareByDescending<LoaiPhong> { it.sucChua}.thenBy{it.giaMotDem })
    }
}
