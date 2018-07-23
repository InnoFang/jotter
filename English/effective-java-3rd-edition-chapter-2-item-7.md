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