This is a quick and dirty client for Image Seek that was put together in a 
couple of hours. It requires that you have an instance of imgSeek: 
http://www.imgseek.net/isk-daemon deployed somewhere. It uses a few hard
coded configuration options (remember, quick and dirty!) that should be
easily changeable for your own deployment.

All the images registered in imgSeek must be hosted at a public URL, the 
mappings between the public URLs and image Ids must be configured in the 
imagedb.csv

Why did I reinvent the wheel when there is already a demo application? 
http://www.imgseek.net/isk-daemon/demo Mainly because I couldn't get the demo
application to deploy and was unsuccessful in obtaining help for it. Maybe 
this will change in the future, maybe not.

===============================================================================

To build a self contained war

mvn package

