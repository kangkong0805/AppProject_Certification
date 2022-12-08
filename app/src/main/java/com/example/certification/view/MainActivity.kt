package com.example.certification.view

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.certification.retrofit.RetrofitClient.service
import com.example.certification.databinding.ActivityMainBinding
import com.example.certification.databinding.ActivityPopupBinding
import com.example.certification.jungchugi.data.Item
import com.example.certification.jungchugi.data.Result
import com.example.certification.jungchugi.data.Time
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarUtils.getMonth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerLogic()
        calendarLogic()
    }

    private fun calendarLogic() {
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val dialog = Dialog(this)
            val dialogBinding = ActivityPopupBinding.inflate(layoutInflater)

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogBinding.root)

            val intent = Intent(Intent.ACTION_VIEW)
            when(binding.certificationSpinner.selectedItem) {
                "정보처리기능사" -> {
                    dialogBinding.name.text = "자격명: 정보처리기능사"
                    dialogBinding.don.text = "응시료: 필기 - 14500원, 실기 - 17200원"
                    dialogBinding.percent.text = "합격률: 필기 - 64.6%, 실기 - 38.05%"
                    dialogBinding.link.text = "링크 바로가기"

                    dialogBinding.link.setOnClickListener {
                        intent.data = Uri.parse("https://www.q-net.or.kr/crf005.do?id=crf00505&jmCd=6921")
                        startActivity(intent)
                    }
                }
                "정보기기운용기능사" -> {
                    dialogBinding.name.text = "자격명: 정보기기운용기능사"
                    dialogBinding.don.text = "응시료: 필기 - 14500원, 실기 - 21800원"
                    dialogBinding.percent.text = "합격률: 필기 - 40.5%, 실기 - 63%"
                    dialogBinding.link.text = "링크 바로가기"

                    dialogBinding.link.setOnClickListener {
                        intent.data = Uri.parse("http://www.q-net.or.kr/crf005.do?id=crf00503&jmCd=6892&gSite=Q&gId")
                        startActivity(intent)
                    }
                }
                "정보처리산업기사" -> {
                    dialogBinding.name.text = "자격명: 정보처리산업기사"
                    dialogBinding.don.text = "응시료: 필기 - 19400원, 실기 - 20800원"
                    dialogBinding.percent.text = "합격률: 필기 - 36.6%, 실기 - 49.3%"
                    dialogBinding.link.text = "링크 바로가기"

                    dialogBinding.link.setOnClickListener {
                        intent.data = Uri.parse("http://www.q-net.or.kr/crf005.do?id=crf00503&jmCd=2290")
                        startActivity(intent)
                    }
                }
                "웹디자인기능사" -> {
                    dialogBinding.name.text = "자격명: 웹디자인기능사"
                    dialogBinding.don.text = "응시료: 필기 - 14500원, 실기 - 20100원"
                    dialogBinding.percent.text = "합격률: 필기 - 79.8%, 실기 - 52.1%"
                    dialogBinding.link.text = "링크 바로가기"

                    dialogBinding.link.setOnClickListener {
                        intent.data = Uri.parse("http://www.q-net.or.kr/crf005.do?id=crf00503&jmCd=7798")
                        startActivity(intent)
                    }
                }
                "전자기기기능사" -> {
                    dialogBinding.name.text = "자격명: 전자기기기능사"
                    dialogBinding.don.text = "응시료: 필기 - 14500원, 실기 - 17200원"
                    dialogBinding.percent.text = "합격률: 필기 - 15.6%, 실기 - 86.2%"
                    dialogBinding.link.text = "링크 바로가기"

                    dialogBinding.link.setOnClickListener {
                        intent.data = Uri.parse("http://www.q-net.or.kr/crf005.do?id=crf00503&jmCd=6790")
                        startActivity(intent)
                    }
                }
            }
            dialog.show()
        }
    }

    private fun spinnerLogic() {
        binding.certificationSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2) {
                    0 -> {  // 정보처리기능사
                        getService(2022, 6921)
                    }
                    1 -> {  // 정보기기운용기능사
                        getService(2022, 6892)
                    }
                    2 -> {  // 정보처리산업기사
                        getService(2022, 2290)
                    }
                    3 -> {  // 전기기능사
                        getService(2022, 7798)
                    }
                    4 -> {  // 전자기기기능사
                        getService(2022, 6790)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("TAG", "onNothingSelected: nothing")
            }

        }
    }


    // retrofit
    private fun getService(implYy: Int, jmCd: Int) {
        // 네트워크 통신 시도 - Call 객체 반환
        service.getParkInfo(
            10, 1, "json",
            implYy, "T", jmCd
        )
            .enqueue(object : Callback<Result> {
                // 통신 성공
                override fun onResponse(call: Call<Result>, result: Response<Result>) {
                    val data = result.body()
                    if (result.isSuccessful && data != null) {
                        Log.d("TAG", "onResponse: $data")

                        setCalender(data.body.items)
                    } else {
                        Log.d("test", "${result.body()}")
                    }
                }

                // 통신 실패
                override fun onFailure(call: Call<Result>, t: Throwable) {
                    Log.e("tlqkf", "onFailure: ${t.printStackTrace()}", t.cause)
                }
            })
    }

    private fun setRecycler(items: List<Item>) { // recyclerview 설정
        val list = arrayListOf<Item>()
        for (i in items.indices) {
            if (items[i].docRegStartDt.isNotEmpty()) {
                list.add(items[i])
            }
        }
        val adapter = ExamScheduleAdapter(list)
        binding.scheduleRecyclerView.adapter = adapter
        binding.scheduleRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun setCalender(items: List<Item>) {
        val docRegStartList = arrayListOf<CalendarDay>()
        val docRegEndList = arrayListOf<CalendarDay>()
        val docExamStartDt = arrayListOf<CalendarDay>()
        val docExamEndDt = arrayListOf<CalendarDay>()
        val docPassDt = arrayListOf<CalendarDay>()

        for (i in items.indices) {
//            Log.d("TAG", "setCalender: ${items[i].docRegStartDt}")
            if (items[i].docRegStartDt.isNotEmpty()) {
                val docRegStart = getData(items[i].docRegStartDt)
                val docRegEnd = getData(items[i].docRegEndDt)
                val docExamStart = getData(items[i].docExamStartDt)
                val docExamEnd = getData(items[i].docExamEndDt)
                val docPass = getData(items[i].docPassDt)

                docRegStartList.add(CalendarDay.from(docRegStart.year, docRegStart.month, docRegStart.day))
                docRegEndList.add(CalendarDay.from(docRegEnd.year, docRegEnd.month, docRegEnd.day))
                docExamStartDt.add(CalendarDay.from(docExamStart.year, docExamStart.month, docExamStart.day))
                docExamEndDt.add(CalendarDay.from(docExamEnd.year, docExamEnd.month, docExamEnd.day))
                docPassDt.add(CalendarDay.from(docPass.year, docPass.month, docPass.day))
            }
        }

        binding.calendarView.removeDecorators()
        for (i in docRegStartList) {    // start
            binding.calendarView.addDecorators(CurrentDayDecorator(this, i))
            setRecycler(items)
        }
        for (i in docRegEndList) {
            binding.calendarView.addDecorators(CurrentDayDecorator(this, i))
            setRecycler(items)
        }
        for (i in docExamStartDt) {
            binding.calendarView.addDecorators(CurrentDayDecorator(this, i))
            setRecycler(items)
        }
        for (i in docExamEndDt) {
            binding.calendarView.addDecorators(CurrentDayDecorator(this, i))
            setRecycler(items)
        }
        for (i in docPassDt) {
            binding.calendarView.addDecorators(CurrentDayDecorator(this, i))
        }

    }

    private fun getData(data: String): Time {
        // 2022 08 19
        Log.d("data12", data)
        if (data != null) {
            val year = data.substring(0, 4).toInt()
            val month = data.substring(4, 6).toInt() - 1
            val day = data.substring(6).toInt()

            Log.d("TAG", "getData: $year, $month, $day")
            return Time(year, month, day)
        }
        return Time(2022, 12, 8)
    }

    private fun getMonth(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val format = SimpleDateFormat("MM")
        return format.format(date)
    }
}