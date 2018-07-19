> Effective Java 3rd Edition 翻译单词学习

# 第 2 章 创建和销毁对象 - 第 2 条 遇到多个构造器参数时要考虑用构建器


## words and phrases

En                                                  | Zh
--------------------------------------------------- | ---- 
Nutrition Facts label                               |营养成分表          
serving size                                        |食用份量                            
total fat                                           |总脂肪量                
saturated fat                                       |饱和脂肪量      
trans fat                                           |转化脂肪            
cholesterol                                         |胆固醇                      
sodium                                              |钠              
telescoping                                         |伸缩式          
telescoping constructor                             |重叠构造函数
For brevity’s sake                                  |为了简洁起见
out of hand                                         |失控
find out                                            |发现;使发作;使受惩罚;通过探询[访问]获悉（某人）不在
identically                                         |同一地，相等地
sequences                                           |一系列;一连串；顺序;次序;序列 ； (电影中描述某一组动作的)连续镜头,片段 ；  (基因)排列顺序，序列
subtle                                              |微妙的;巧妙的;敏感的;狡猾的
misbehave                                           |行为不端;  
inconsistency                                       |不一致，不协调;前后矛盾，不一贯
mandates                                            |要求;	命令; 任务
mutability                                          |易变性，性情不定;突变性;易弯性
wordy                                               |唠叨的; <贬>冗长的; 絮叨; 数黑论黄
simulate                                            |模拟
Validity                                            |效力; 正确性; 有效，合法性; 正确，正当
omitted                                             |遗漏; 省略( omit的过去式和过去分词 ); 删掉; 忘记
brevity                                             |简洁; 短暂
invariants	                                        |不变性;不变式;不变量;不变数据;不变关系
hierarchies                                         |等级制度( hierarchy的名词复数 ); 统治集团; 领导
workaround                                          |工作区；解决方法
simulated                                           |假装的; 冒充的; 仿造的; 模仿的; 假装( simulate的过去式和过去分词 ); 模仿; 模拟
idiom                                               |习语，成语; 方言，土语; 风格; 惯用语法
calzone                                             |半圆形烤（乳酪）馅饼
former                                              |以前的，从前的; 在前的; 前任的; 模型，样板; 构成者，创造者; 起形成作用的人; 线圈架
is declared to                                      |被声明为
wherein                                             |其中; 在那里，在哪方面; 在哪一点上; 在什么地方
covariant                                           |协变式的，协变的
hierarchical                                        |分层的; 按等级划分的，等级的
identical to                                        |与…相同
essentially                                         |本质上，根本上; 本来; “essential”的派生
assumes                                             |假定; 假设; 呈现; 取得( assume的第三人称单数 )
minor                                               |较小的，少数的，小…; 未成年的; 小调的，小音阶的; 年幼的; 未成年人; 副修科目; 小
Alternatively                                       |或者; 二者择一地; 要不然
aggregate                                           |骨料; 合计; 聚集体; 集料; 总数的，总计的; 聚合的; 聚成岩的; 使聚集，使积聚; 总
tweak                                               |微调
upon                                                |在…上面; 当…时候
serial number                                       |序号，序列号，轴号
noticeable                                          |显而易见的，明显的; 引人注目的，令人瞩目的; 显著的，重要的; 可以察觉的
out of hand                                         |无法控制; 立即; 终于; 难以收拾
stick out like a sore thumb                         |显得别扭
start with                                          |以…开始
in the first place                                  |首先，从一开始; 压根儿; 固; 当初
evolves                                             |演变，进化( evolve的第三人称单数 ); 进化，进化形成
obsolete                                            |过时的

## Translation

En                                                           | Zh
-------------------------------------------------------------|---
Validity checks were omitted	for	brevity                  | 为了简洁，省略了有效性检查。