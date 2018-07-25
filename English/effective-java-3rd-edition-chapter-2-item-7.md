> Effective Java 3rd Edition 翻译单词学习

# 第 7 条：消除过期对象引用

## words and phrases

 + ***Eliminate** **obsolete** object references*

    - eliminate : 消除<possibility, hypothesis, suspect>；消除<error, risk, waste, unnecessary expenditure>；消灭<disease>；除掉<enemy, opponent, rival>；淘汰；排出
    - obsolete : 淘汰的<machine, techonology>；过期的<passport, ticket>；过时的<expression, law, practice>；退化的<limb, organ>

    > 消除过期对象引用

+ *your objects are automatically **reclaimed***

    - reclaimed : (for cultivation, building)开垦<marshand, desert, waste ground>；(recycle)回收<glastic, glass>；(recover)拿回<possessions, luggage>

    > 你的对象被自动回收

+ *when you're **through with** them*

+ *It can easily **lead to** the **impression** that you don’t have to think about memory management, but this isn't  quite true*

    - lead to : 导致; 把…带到; 领到; 通向
    - impression : 印象，感觉; 影响，效果; 盖印，印记

    > 它很容易给你留下这样的印象，你不需要去考虑内存管理这件事请，然而事实并非如此

+ *you could test it **exhaustively**, and it would pass every test **with flying colors**, but there’s a problem **lurking***

    - exhaustively : 彻底地<search>；全面地<study, research>；详尽地<describe, comment>
    - with flying colors : 出色地
    - lurking : (lie in wait)潜伏；(loiter)闲逛；(linger)潜藏；(in a chatroom or forum)潜水；潜藏的

    > 你可以对它进行全面的测试，它可以出色地通过各种测试，但（程序中）潜藏着一个问题

+ *	if they are **subsequently** **dereferenced** by mistake, the program will immediately fail with a NullPointerException*

    - subsequently : 其后，随后，接着; “subsequent”的派生; 嗣后; 尔后
    - dereferenced : 解除引用

    > 如果它们之后被错误地解除引用，程序将会立刻失败，并抛出 NullPointerException 异常

+ *When	programmers	are	first	**stung**	by	this	problem,	they	may	**overcompensate** by	**nulling**	out	every	object	reference	as	soon	as	the	program	is	finished	using	it.*

    - stung : 螫伤，刺伤( sting的过去式和过去分词 ); 感到剧痛; 激怒; 使不安
    - overcompensate : overcompensate过度代偿
    - nulling : 指零

    > 当程序员第一次被这个问题所困扰时，他们可能会在程序使用完毕后立即清空每个对象引用，从而过度补偿。

+ *it	**clutters up**	the	program	unnecessarily*

    - clutter up : 使散乱

    > 它会对程序造成不必要地干扰

+ *Nulling	out	object	references	should	be	the	**exception**	rather	than	the	**norm***

    - exception : 例外，除外; 反对，批评; 异议，反对
    - norm : 规范; 标准; 准则; 定额

    > 清空对象引用应该是例外，而非规范

+ *What	aspect	of	the	Stack	class makes	it	**susceptible**	to	memory	leaks*

    - susceptible : 易受影响的; 易受感染的; 善感的; 可以接受或允许的

    > Stack 类的哪个方面使其易受内存泄漏的影响呢

+ ***Simply	put**,	it	manages	its	own	memory*

    - simply put : 简单地说

    > 简单的说，（问题在于，）它管理它自己的内存（manage its own memory）

+ *those in the **remainder** of the array	are free*

    - remainder : 剩余物; 其他人员; 差数; 廉价出售的图书; 廉价出售; 剩余的; 留存下的 

    > 数组其余部分则是自由的（free）

+ *with entries	becoming	less	valuable	over	time.*

    > 条目随着时间的推移变得不那么有价值

+ ***Under	these	circumstances**,	the	cache should	**occasionally**	**be	cleansed	of**	entries	that	have	**fallen	into**	**disuse***

    - Under these circumstances : 在这种情况下
    - occasionally : 偶尔; 偶然; 有时候
    - be cleansed of : 弄干净，清洗
    - fallen into : 分成，开始；变成
    - disuse : 不用，废弃

    > 在这种情况下，缓存应该偶尔清除已经不用的项

+ *The	LinkedHashMap	class	**facilitates**	the	latter	approach with	its	removeEldestEntry	method.	For	more	**sophisticated**	caches,	you may	need	to	use	java.lang.ref	directly*

    - facilitates : 促进( facilitate的第三人称单数 );使便利;推进;帮助（某人）进步
    - sophisticated : adj. 复杂的;精致的;富有经验的;深奥微妙 v.使变得世故;使迷惑;篡改(sophisticate的过去分词形式)

    > LinkedHashMap 类使用 removeEldestEntry 方法来促进后一种方法。 对于更复杂的缓存，您可能需要直接使用 java.lang.ref。 

+ *ensure	that	callbacks	are	garbage	collected	**promptly***

    - promptly : 迅速地;敏捷地;立即地;毫不迟疑

    > 确保回调被及时的垃圾回收

+ *They	are	typically discovered	only	as	a	result	of	careful	code	**inspection**	or	with	the	**aid	of**	a debugging	tool	known	as	a	heap	profile*

    - inspection : 检验;检查;视察;检阅
    - aid of : 借助

    > 它们通常仅在仔细的代码检查或者借助被称为 heap profile 的调试工具的情况下才被发现