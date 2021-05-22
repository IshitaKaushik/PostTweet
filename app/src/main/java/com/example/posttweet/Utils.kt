package com.example.posttweet

class Utils {
    companion object{
        private const val second = 1000
        private const val minute=60* second
        private const val hour=60* minute
        private const val days=24* hour

        fun getTime(time : Long): String?{
            val now:Long=System.currentTimeMillis()
            if(time>now || time <= 0){
                return null
            }
            val diff=now-time
            return if (diff < minute){
                "just now"
            }
            else if (diff < 2* minute){
                "a minute ago"
            }
            else if (diff < 50* minute){
                    (diff/ minute).toString()+" minutes ago"
                }
            else if(diff < 90* minute){
                "an hour ago"
            }
            else if (diff < 24* hour){
                (diff/ hour).toString()+" hours ago"
            }
            else if (diff < 48* hour){
                "yesterday"
            }
            else{
                (diff/ days).toString()+" days ago"
            }
        }
    }
}