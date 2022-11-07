# open_ffmpeg_flv_android_LAN
Flv streaming service application based on ffmpeg Android platform, characterized by

the command is : ffmpeg -re -i 1.mp4 -vcodec copy -f flv -listen 1 http://0.0.0.0:8000  ,then use flv decode and play.

now ,it is android platform impl with screencast

done: flv
todo: rtsp,见java代码实现的https://github.com/pedroSG94/rtmp-rtsp-stream-client-java



ffmpeg 没有mediacodec的硬编码的实现，添加时比较复杂的，但是也有实现demo，https://github.com/iocaster/ffmpeg-4.0.mc264，surface可能还从pbo那copy了一次，不太清楚
在之后flv封包中，有一些java的实现如来疯直播中的FlvPacker，和native的实现如https://github1s.com/tencentyun/media-server/blob/HEAD/libflv/Makefile，flv的什么header，tag较为简单
故不使用ffmpeg来做了，但今后可以使用ffmpeg-command可以对已有视频（本地file，流媒体转播）进行直播，如ffmpeg-kti库支持，比gstreamer方便。
http转播使用了https://github.com/koush/AndroidAsync/issues/606， write.ByteBufferList即可。
flv播放需要header，可以缓存header给下一个客户端，因为最好只是单客户端，毕竟http是基于tcp的一对一阻塞式稳定传输。
