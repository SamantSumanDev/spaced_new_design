package com.example.stack1trail



import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView
import com.bumptech.glide.Glide
import com.example.stack1trail.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var DeprtTimeRecyclerView: RecyclerView
    private lateinit var deprtAdapter: CustomAdapter

    private lateinit var aAdapter: MyAdapter
    private lateinit var secondMyAdapter: SecondMyAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var SecondViewPager: ViewPager2
    private lateinit var childCheckBox: CheckBox
    private lateinit var gifImageView: ImageView
    private lateinit var firstView: ConstraintLayout
    private lateinit var firstViewBt: ConstraintLayout
    private lateinit var secondView: ConstraintLayout
    private lateinit var secondViewMiddle: ConstraintLayout
    private lateinit var secondViewBottom: ConstraintLayout
    private lateinit var thirdView: ConstraintLayout
    private lateinit var thirdViewThird: ConstraintLayout
    private lateinit var fourthView: ConstraintLayout
    private lateinit var txtActionBar: AppCompatTextView
    private lateinit var calendar: DateRangeCalendarView
    private lateinit var imgFirstViewUpArrow: AppCompatImageView
    private lateinit var secondViewDepartureDate: AppCompatTextView
    private lateinit var secondViewDepartureTime: AppCompatTextView
    private lateinit var thirdViewDepartureDate: AppCompatTextView
    private lateinit var thirdViewDepartureTime: AppCompatTextView
    private lateinit var thirdViewAdults: AppCompatTextView
    private lateinit var thirdViewChildren: AppCompatTextView
    private lateinit var tvTotalTickets: AppCompatTextView
    private lateinit var tvDone: AppCompatTextView
    private lateinit var progressBarAnimation: LottieAnimationView
    private lateinit var thSecondView: ConstraintLayout


    var selected_date_range = ""
    var selected_departure_time = ""

    var selected_adults: Int = 0
        set(value) {
            field = value
            updateTotalTickets()
        }


    var selected_children: Int = 0
        set(value) {
            field = value
            updateTotalTickets()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }


        initView()

        ViewTransitionA()

        firstViewInit()

        secondViewInit()


        val resourceId = resources.getIdentifier("face_scane", "drawable", packageName)

        Glide.with(this)
            .asGif()
            .load(resourceId)
            .into(gifImageView)

    }

    private fun initView() {
        DeprtTimeRecyclerView = binding.departureTimeRecyclerView
        viewPager = binding.viewPager
        childCheckBox = binding.childCheckbox
        SecondViewPager = binding.SecondViewPager
        gifImageView = binding.gifImageView
        firstView = binding.firstView
        firstViewBt = binding.firstViewBt
        secondView = binding.secondView
        secondViewMiddle = binding.secondViewMiddle
        secondViewBottom = binding.secondViewBottom
        thirdView = binding.thirdView
        thirdViewThird = binding.thirdViewThird
        fourthView = binding.fourthView
        txtActionBar = binding.txtActionBar
        calendar = binding.calendar
        imgFirstViewUpArrow = binding.imgFirstViewUpArrow
        secondViewDepartureDate = binding.secondViewDepartureDate
        secondViewDepartureTime = binding.secondViewDepartureTime
        thirdViewDepartureDate = binding.thirdViewDepartureDate
        thirdViewDepartureTime = binding.thirdViewDepartureTime
        thirdViewAdults = binding.thirdViewAdults
        thirdViewChildren = binding.thirdViewChildren
        tvTotalTickets = binding.tvTotalTickets
        tvDone = binding.tvDone
        thSecondView = binding.thSecondView

    }

    private fun ViewTransitionA() {
        val second_middle_slide_up =
            AnimationUtils.loadAnimation(this, R.anim.second_middle_slide_up)
        val second_bottom_slide_up =
            AnimationUtils.loadAnimation(this, R.anim.second_bottom_slide_up)
        val th_slide_up = AnimationUtils.loadAnimation(this, R.anim.th_slide_up)
        val fourth_slide_up = AnimationUtils.loadAnimation(this, R.anim.fourth_slide_up)

        val th_slide_dowon = AnimationUtils.loadAnimation(this, R.anim.th_collapse_top_to_bottom)


        firstViewBt.setOnClickListener {
            if (!(selected_departure_time.isNullOrEmpty()) && !(selected_date_range.isNullOrEmpty())) {
                firstView.visibility = View.GONE
                secondView.visibility = View.VISIBLE
                thirdView.visibility = View.GONE
                fourthView.visibility = View.GONE

                secondViewMiddle.startAnimation(second_middle_slide_up)
                secondViewBottom.startAnimation(second_bottom_slide_up)

                txtActionBar.setText("SEATS")
            }
        }

        secondViewBottom.setOnClickListener {
            if (!(selected_adults.equals(0)) || !(selected_children.equals(0))) {

                firstView.visibility = View.GONE
                secondView.visibility = View.GONE
                thirdView.visibility = View.VISIBLE
                fourthView.visibility = View.GONE

                thirdViewThird.startAnimation(th_slide_up)
                txtActionBar.setText("PAY")
            }
        }

        thirdViewThird.setOnClickListener {
            firstView.visibility = View.GONE
            secondView.visibility = View.GONE

            fourthView.visibility = View.VISIBLE

            fourthView.startAnimation(fourth_slide_up)

        }

        tvDone.setOnClickListener {
            firstView.visibility = View.VISIBLE
            secondView.visibility = View.GONE
            thirdView.visibility = View.GONE
            fourthView.visibility = View.GONE
        }

        thSecondView.setOnClickListener {
            thirdViewThird.startAnimation(th_slide_dowon)
            th_slide_dowon.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    // Animation start actions (if needed)
                }

                override fun onAnimationEnd(animation: Animation) {
                    // Animation end actions (if needed)
                    firstView.visibility = View.GONE
                    secondView.visibility = View.VISIBLE
                    thirdView.visibility = View.GONE
                    fourthView.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {
                    // Animation repeat actions (if needed)
                }
            })

        }



    }

    private fun firstViewInit() {
        val current: Calendar = Calendar.getInstance()
        calendar.setCurrentMonth(current)

        calendar.setCalendarListener(object : CalendarListener,
            com.archit.calendardaterangepicker.customviews.CalendarListener {

            override fun onFirstDateSelected(startDate: android.icu.util.Calendar) {

            }

            override fun onDateRangeSelected(
                startDate: android.icu.util.Calendar,
                endDate: android.icu.util.Calendar
            ) {

            }

            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
                val startDateStr = dateFormat.format(startDate.time)
                val endDateStr = dateFormat.format(endDate.time)
                selected_date_range = "$startDateStr - $endDateStr"
                //Toast.makeText(applicationContext, selected_date_range, Toast.LENGTH_SHORT).show()
                secondViewDepartureDate.text = selected_date_range
                thirdViewDepartureDate.text = selected_date_range

                if (!(selected_departure_time.isNullOrEmpty()) && !(selected_date_range.isNullOrEmpty())) {
                    firstViewBt.setBackgroundResource(R.drawable.first_bm_bg)
                    imgFirstViewUpArrow.visibility = View.VISIBLE

                } else {
                    firstViewBt.setBackgroundResource(R.drawable.first_bm_bg_a)
                    imgFirstViewUpArrow.visibility = View.GONE
                }

            }

            override fun onFirstDateSelected(startDate: Calendar) {

            }
        })


        val itemList = listOf(
            CustomAdapter.Item("05:00 AM"),
            CustomAdapter.Item("12:30 PM"),
            CustomAdapter.Item("06:00 PM"),
            CustomAdapter.Item("09:00 PM"),
            CustomAdapter.Item("11:00 PM"),
            CustomAdapter.Item("03:00 AM")
        )

        val deprtAdapter = CustomAdapter(itemList) { clickedItemData ->
            selected_departure_time = clickedItemData
            secondViewDepartureTime.text = selected_departure_time
            thirdViewDepartureTime.text = selected_departure_time
            if (!(selected_departure_time.isNullOrEmpty()) && !(selected_date_range.isNullOrEmpty())) {
                firstViewBt.setBackgroundResource(R.drawable.first_bm_bg)
                imgFirstViewUpArrow.visibility = View.VISIBLE

            } else {
                firstViewBt.setBackgroundResource(R.drawable.first_bm_bg_a)
                imgFirstViewUpArrow.visibility = View.GONE

            }
            // Toast.makeText(this, "Clicked item data: $clickedItemData", Toast.LENGTH_SHORT).show()
        }

        DeprtTimeRecyclerView.adapter = deprtAdapter
        DeprtTimeRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


    }

    private fun secondViewInit() {
        val items = ArrayList<MyAdapter.Item>()
        items.add(MyAdapter.Item("1"))
        items.add(MyAdapter.Item("2"))
        items.add(MyAdapter.Item("3"))
        items.add(MyAdapter.Item("4"))
        items.add(MyAdapter.Item("5"))
        items.add(MyAdapter.Item("6"))
        items.add(MyAdapter.Item("7"))
        items.add(MyAdapter.Item("8"))
        items.add(MyAdapter.Item("9"))

        val secondItems = ArrayList<SecondMyAdapter.Item>()
        secondItems.add(SecondMyAdapter.Item("1"))
        secondItems.add(SecondMyAdapter.Item("2"))
        secondItems.add(SecondMyAdapter.Item("3"))
        secondItems.add(SecondMyAdapter.Item("4"))
        secondItems.add(SecondMyAdapter.Item("5"))
        secondItems.add(SecondMyAdapter.Item("6"))
        secondItems.add(SecondMyAdapter.Item("7"))
        secondItems.add(SecondMyAdapter.Item("8"))
        secondItems.add(SecondMyAdapter.Item("9"))

        // sliderHandler = Handler(Looper.myLooper()!!)

        aAdapter = MyAdapter(items, viewPager)
        viewPager.adapter = aAdapter


        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        //  viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        setUpTransformer()


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                aAdapter.setSelectedItem(position)
                selected_adults = items[position].text.toInt()
                val setSelectedAdults = items[position].text + " ADULTS"
                thirdViewAdults.text = setSelectedAdults
            }
        })


        secondMyAdapter = SecondMyAdapter(secondItems, SecondViewPager)
        SecondViewPager.adapter = secondMyAdapter


        SecondViewPager.offscreenPageLimit = 3
        SecondViewPager.clipToPadding = false
        SecondViewPager.clipChildren = false
        //  viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        setSCUpTransformer()


        SecondViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                secondMyAdapter.setSelectedItem(position)
                selected_children = secondItems[position].text.toInt()
                val setSelectedChildren = ", " + secondItems[position].text + " CHILDREN"
                thirdViewChildren.text = setSelectedChildren
            }
        })



        childCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                SecondViewPager.visibility = View.VISIBLE
                viewPager.setPadding(450, 0, 450, 0)
                val selectedTxtSize = 80
                val unSelectedTxtSize = 40
                aAdapter.setTextSize(unSelectedTxtSize.toFloat())
                aAdapter.setSelectedTextSize(selectedTxtSize.toFloat())

            } else {
                SecondViewPager.visibility = View.GONE
                viewPager.setPadding(370, 0, 370, 0)
                val selectedTxtSize = 160
                val unSelectedTxtSize = 80
                aAdapter.setTextSize(unSelectedTxtSize.toFloat())
                aAdapter.setSelectedTextSize(selectedTxtSize.toFloat())

                // Checkbox is unchecked
                // Perform actions when the option is deselected
            }
        }


    }

    private fun updateTotalTickets() {
        val totalTickets = selected_adults + selected_children
        tvTotalTickets.text = totalTickets.toString() + " Flight Tickets \n to Santorini"

    }


    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(0))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager.setPageTransformer(transformer)
    }

    private fun setSCUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(0))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        SecondViewPager.setPageTransformer(transformer)
    }


}


