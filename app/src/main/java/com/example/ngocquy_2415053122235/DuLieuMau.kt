package vn.edu.ute.datphong.data
import vn.edu.ute.datphong.model.LoaiPhong
object DuLieuMau {
    val dsLoaiPhong: List<LoaiPhong> = listOf(
        LoaiPhong(ma ="P235", ten = "Phòng Tiêu Chuẩn P235", giaMotDem = 500000L, sucChua= 2),
        LoaiPhong(ma = "P102", ten = "Phòng Superior P102", giaMotDem = 8000000, sucChua = 3),
        LoaiPhong(ma = "P103", ten = "Phòng Deluxe P103", giaMotDem= 1200000L, sucChua=4),
        LoaiPhong(ma = " P104", ten = "Phòng Vip Suite P104", giaMotDem=2500000L, sucChua=4)
    )
}