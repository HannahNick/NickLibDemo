-optimizationpasses 5
-dontwarn java.awt.*

# See http://stackoverflow.com/questions/5701126, happens in dnsjava
-keep class android.support.v7.widget.SearchView { *; }
-keep class com.nick.dnschanger.util.GenericFileProvider
-keep class com.nick.dnschanger.activities.PinActivity
-keep class android.support.v7.app.AppCompatViewInflater {
    *;
}
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-dontwarn org.slf4j.impl.*
-keep class org.slf4j.** {
    *;
}
-keep class org.pcap4j.** {
    *;
}
-assumenosideeffects class org.slf4j.Logger {
    public void debug(...);
    public void trace(...);
}