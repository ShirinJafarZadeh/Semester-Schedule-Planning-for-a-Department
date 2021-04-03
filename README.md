&#x202b; می خواهیم با توجه به محدودیت های آموزشی دانشجویان و برنامه ی پیشنهادی اساتید یک برنامه ی درسی برای یک ترم
دانشکده ارائه دهیم به گونه ای که در آن هم دروس خاصی با هم تلاقی زمانی نداشته باشند و هم طبیعتا دروس ارائه شده توسط یک
استاد با هم تلاقی نداشته باشند

&#x202b; روش هایی که برای حل این مسئله باید با هم مقایسه کنید عبارت اند از

&#x202b; الگوریتم backtrack  به همراه forward checking ( BT-FC )

&#x202b; الگوریتم backtrack به همراه forward checking و MRV ( BT-FC-MRV )

&#x202b; پیش پردازش با AC-3 و سپس الگوریتم backtrack به همراه FC و MRV (AC3+BT-FC-MRV)

&#x202b; درحالت پایه )یعنی زمانی که از هیچ هیوریستیکی استفاده نمی کنید(، ترتیب متغیرها برابر با ترتیب پیمایش سطری و از چپ به
راست در نظر گرفته شود. همچنین ترتیب مقادیر نیز ترتیب صعودی اعداد باشد.

&#x202b; ورودی: فایل متنی (.txt)  است که در سطر اول آن به ترتیب اعداد B تعداد دروس، T تعداد اساتید و M تعداد محدودیت های
تداخلی کلاس ها می آید.
4, 3, 2

&#x202b; در سطر بعدی نام دروس ذکر می شود:
CSE101, CSE103, CSE106, CSE112

&#x202b; 
سپس T سطر می آید که در سطر iام ابتدا عدد k و سپس k شماره ی درس می آید که دروس مورد علاقه ی استاد برای ارائه در
ترم مورد نظر است:

1, CSE101
2, CSE106, CSE103
1, CSE112

&#x202b; پس از آن دوباره T سطر می آید که در سطر iام ابتدا عدد k و سپس k عدد بین 2 تا 19 می آید که زمان های خالی استاد i ام برای
ارائه ی دروس می باشد. (فرض شده است که در هر یک از روزهای شنبه تا چهارشنبه، چهار کلاس می تواند برگزار شود. برای مثال
28 می باشد.) - 29 و عدد 19 نشان دهنده ی چهارشنبه 21 - عدد یک نشان دهنده ی شنبه 8

6, 7, 8, 9, 10, 11, 12
6, 7, 8, 9, 10, 11, 12
1, 10

&#x202b; درنهایت M سطر می آید که در هر سطر ابتدا عدد k و پس از آن شماره ی k درس می آید که برنامه ی زمانی آن k درس نباید با هم
تلاقی زمانی داشته باشند

3, CSE101, CSE103, CSE106
2, CSE103, CSE112

&#x202b; اجرای برنامه باید بتواند از طریق خط فرمان صورت گیرد و به هنگام اجرای برنامه شماره الگوریتم و فایل ورودی مشخص شود برای
مثال
C:\Ex> Scheduling input.txt 2

&#x202b; متناظر با اجرای الگوریتم BT-FC-MRV بر روی فایل ورودی input.txt است. درصورتی که هر یک از الگوریتم ها را پیاده سازی
« نکرده اید می بایست به ازای شماره ی مربوطه در خروجی پیغام No implementation چاپ شود. تا حد ممکن از »
ساختمان داده هایی استفاده کنید که سرعت مناسبی را برای پیاده سازی روش ها فراهم کند.
خروجی: اگر مسئله جواب نداشت خروجی رشته ی No Solution خواهد بود، در غیر این صورت خروجی B سطر است که در هر
سطر سه عدد Bi ، Ti و Ii به ترتیب می آید بدین معنی که درس Bi باید توسط استاد Ti در زمان Ii ارائه شود.

CSE101, 1, 10
CSE106, 2, 11
CSE112, 3, 10
CSE103, 2, 12

&#x202b; در مساله فوق از یک شرط ساده سازی استفاده کردیم، اکنون برای کلی تر شدن برنامه ی خود فرض کنید که ممکن
است علایق اساتید مشابه هم باشد. در این صورت باید توجه کرد که هر درس حتما باید توسط یک استاد ارائه شود. این محدودیت
یک محدودیت کلی است و باید با تکنیکی آن را به چند محدودیت دوتایی شکست. فرض کنید برای ارائه ی درس Bi سه استاد Ti1 ،
Ti2 و Ti3 اعلام آمادگی کرده اند. یک متغیر جدید تعریف کرده و دامنه ی آن را {1,2,3} در نظر بگیرید. بین این متغیر جدید و هر
یک از سه متغیر مربوط به آن درس یک محدویت تعریف کنید بدین صورت که اگر درس توسط استاد Tik تدریس شود آن گاه متغیر
جدید مجبور است که مقدار k به خود بگیرد و بالعکس. مثلا اگر این درس توسط استاد Ti1 تدریس نشود متغیر جدید مقدار 2 یا 3 را
می تواند به خود بگیرد.
پیاده سازی خود در بخش قبل را به گونه ای تغییر دهید تا در این حالت نیز به درستی عمل کند.




