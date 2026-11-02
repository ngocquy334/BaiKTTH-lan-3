package vn.edu.ute.datphong.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ngocquy_2415053122235.R
import vn.edu.ute.datphong.data.DuLieuMau
import vn.edu.ute.datphong.helper.DatPhongHelper
import vn.edu.ute.datphong.helper.dinhDangTien
import vn.edu.ute.datphong.model.LoaiPhong

class ChonPhongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chon_phong)

        val soKhach = intent.getIntExtra("so_khach", 1)
        val rgLoaiPhong = findViewById<RadioGroup>(R.id.rgLoaiPhong)
        val btnChonXong = findViewById<Button>(R.id.btnChonXong)

        val dsPhuHop = DatPhongHelper.locTheoSoKhach(DuLieuMau.dsLoaiPhong, soKhach)

        dsPhuHop.forEach { loai ->
            val rb = RadioButton(this).apply {
                id = android.view.View.generateViewId()
                text = "${loai.ten} - Sức chứa: ${loai.sucChua} người - Giá: ${loai.giaMotDem.dinhDangTien()}/đêm"
                tag = loai.ma
            }
            rgLoaiPhong.addView(rb)
        }

        btnChonXong.setOnClickListener {
            val checkedId = rgLoaiPhong.checkedRadioButtonId
            if (checkedId == -1) {
                Toast.makeText(this, "Vui lòng chọn loại phòng", Toast.LENGTH_SHORT).show()
            } else {
                val rbSelected = findViewById<RadioButton>(checkedId)
                val maLoaiPhong = rbSelected.tag.toString()

                val resultIntent = Intent().apply {
                    putExtra("ma_loai_phong", maLoaiPhong)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}