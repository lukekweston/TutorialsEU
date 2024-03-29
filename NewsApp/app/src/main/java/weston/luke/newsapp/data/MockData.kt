package weston.luke.newsapp.data

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import weston.luke.newsapp.R
import java.util.*

object MockData {
    val topNewsList = listOf<NewsData>(
        NewsData(
            1,
            author = "Raja Razek, CNN",
            title = "'Tiger King' Joe Exotic says he has been diagnosed with aggressive form of prostate cancer - CNN",
            description = "Joseph Maldonado, known as Joe Exotic on the 2020 Netflix docuseries \\\"Tiger King: Murder, Mayhem and Madness,\\\" has been diagnosed with an aggressive form of prostate cancer, according to a letter written by Maldonado.",
            publishedAt = "2021-11-04T05:35:21Z"
        ),
        NewsData(
            2,
            author = "Namita Singh",
            image = R.drawable.namita,
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ),
        NewsData(
            3,
            author = "Not available",
            image = R.drawable.reuters,
            title = "'You are not alone': EU Parliament delegation tells Taiwan on first official visit - Reuters",
            description =
            "The European Parliament's first official delegation to Taiwan said on Thursday the diplomatically isolated island is not alone and called for bolder actions to strengthen EU-Taiwan ties as Taipei faces rising pressure from Beijing.",
            publishedAt = "2021-11-04T03:37:00Z"
        ),
        NewsData(
            4,
            author = "Mike Florio",
            image = R.drawable.michael,
            title = "Aaron Rodgers violated COVID protocol by doing maskless indoor press conferences - NBC Sports",
            description = "Packers quarterback Aaron Rodgers has been conducting in-person press conferences in the Green Bay facility without wearing a mask. Because he was secretly unvaccinated, Rodgers violated the rules.",
            publishedAt = "2021-11-04T03:21:00Z"
        ),
        NewsData(
            5,
            author = "Grant Brisbee",
            title = "Buster Posey's career was like no other in Giants history - The Athletic",
            description = "There was a franchise without Buster Posey, and there was one with him, and those two franchises were radically, impossibly different.",
            publishedAt = "2021-11-04T02:12:54Z"
        ),
        NewsData(
            6,
            author = "Michael Schneider",
            image = R.drawable.michael,
            title = "‘The Masked Singer’ Reveals Identity of the Beach Ball: Here Are the Stars Under the Mask - Variety",
            description = "SPOILER ALERT: Do not read ahead if you have not watched “The Masked Singer” Season 6, Episode 8, “Giving Thanks,” which aired November 3 on Fox. Honey Boo Boo, we hardly knew you. Reality TV mother and daughter stars June Edith “Mama June” Shannon and Alana …",
            publishedAt = "2021-11-04T02:00:00Z"
        ),
        NewsData(
            7,
            author = "Thomas Barrabi",
            title = "Sen. Murkowski slams Dems over 'show votes' on federal election bills - Fox News",
            description = "Sen. Lisa Murkowski, R-Alaska, slammed Senate Democrats for pursuing “show votes” on federal election bills on Wednesday as Republicans used the filibuster to block consideration the John Lewis Voting Rights Advancement Act.",
            publishedAt = "2021-11-04T01:57:36Z"
        ),
        NewsData(
            8,
            author = "CBSBoston.com Staff",
            title = "Principal Beaten Unconscious At Dorchester School; Classes Canceled Thursday - CBS Boston",
            description = "Principal Patricia Lampron and another employee were assaulted at Henderson Upper Campus during dismissal on Wednesday.",
            publishedAt = "2021-11-04T01:55:00Z"
        )
    )

    fun getNews(newsId: Int?): NewsData {
        return topNewsList.first { it.id == newsId }
    }
    fun Date.getTimeAgo(): String {
        val calendar = Calendar.getInstance()
        calendar.time = this

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)

        val currentCalendar = Calendar.getInstance()
        val currentYear = currentCalendar.get(Calendar.YEAR)
        val currentMonth = currentCalendar.get(Calendar.MONTH)
        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
        val currentMin = currentCalendar.get(Calendar.MINUTE)

        return if (year < currentYear) {
            val interval = currentYear - year
            if (interval == 1) "$interval year ago" else "$interval years ago"
        } else if (month < currentMonth) {
            val interval = currentMonth - month
            if (interval == 1) "$interval month ago" else "$interval months ago"
        } else if (day < currentDay) {
            val interval = currentDay - day
            if (interval == 1) "$interval month ago" else "$interval months ago"
        } else if (hour < currentHour) {
            val interval = currentHour - hour
            if (interval == 1) "$interval hour ago" else "$interval hours ago"
        } else if (min < currentMin) {
            val interval = currentMin - min
            if (interval == 1) "$interval minute ago" else "$interval minutes ago"
        } else {
            " a moment ago"
        }
    }


    fun stringToDate(publishedAt: String): Date {
        val date =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssxx", Locale.ENGLISH).parse(publishedAt)
            } else {
                java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssxx", Locale.ENGLISH)
                    .parse(publishedAt)
            }
        Log.d("published", "$date")
        return date
    }


    val techNewsList = listOf<NewsData>()
    val sportsNewsList = listOf<NewsData>()
    val businessNewsList = listOf<NewsData>()
    val deNewsList = listOf<NewsData>()
    val usNewsList = listOf<NewsData>()
    val frNewsList = listOf<NewsData>()

}