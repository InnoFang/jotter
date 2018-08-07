import os
import re
import time
from collections import defaultdict

lines = 0
pattern = re.compile('.*\.(py|java|clj|html|c|cpp|js|pde|kt)$')
file_dict = defaultdict(int)


def countFileLines(filename):
    count = 0
    with open(filename, 'rb') as handle:
        for _ in handle:
            count += 1
    return count


def listDir(path):
    global lines, pattern, file_dict
    files = os.listdir(path)
    for file in files:
        file_path = os.path.join(path, file)
        if os.path.isdir(file_path):
            listDir(file_path)
        elif os.path:
            try:
                res = re.match(pattern, file)
                if res:
                    line = countFileLines(file_path)
                    file_type = os.path.splitext(file)[1][1:]
                    file_dict[file_type] += 1
                    print('File name: ', file_path, ' , file type: ', file_type, ' , code line: ', line)
                    lines += line
            except AttributeError as e:
                print(e)


def main():
    global lines
    time_start = time.time()
    print('--- searching and counting ---')
    listDir('F:/AS Project/GankIODemo')
    listDir('F:/AS Project/AutoSMS')
    listDir('F:/HTML')
    listDir('F:/IDEA/Quantify')
    listDir('F:/IDEA/TSDMS')
    listDir('F:/IDEA/LearnKotlin')
    listDir('F:/IDEA Project/Design Patterns')
    listDir('F:/IDEA Project/LanQiao')
    listDir('F:/IDEA/Algorithms')
    listDir('F:/PyCharm Project/Algo4ML')
    listDir('F:/MyApplication')
    listDir('F:/opencv3_project')
    listDir('F:/Github/playground')
    listDir('F:/python')
    listDir('F:/repositories/ChangTuTest')
    listDir('F:/repositories/Android-Code')
    listDir('F:/repositories/KoolWeather')
    listDir('F:/repositories/ProtectPlus')
    listDir('F:/repositories/PartyBuildingStudies')
    listDir('F:/Processing Project/AlgorithmVisualizer')
    listDir('F:/train')
    print('Total number of lines of code: ', lines)
    time_end = time.time()
    totally = 0

    # py|java|clj|html|c|cpp|js|pde|kt
    for _, count in file_dict.items():
        totally += count
    for tp, count in file_dict.items():
        print('The number of ', tp, ' file: ', count, ' , ratio is %.2f%%' % (count / totally * 100))
    print('Totally cost: ', time_end - time_start, 's')


if __name__ == '__main__':
    main()
    """
    Total number of lines of code:  2288741
    The number of  java  file:  3592  , ratio is 64.86%
    The number of  cpp  file:  76  , ratio is 1.37%
    The number of  c  file:  39  , ratio is 0.70%
    The number of  clj  file:  5  , ratio is 0.09%
    The number of  pde  file:  77  , ratio is 1.39%
    The number of  html  file:  231  , ratio is 4.17%
    The number of  py  file:  1314  , ratio is 23.73%
    The number of  kt  file:  204  , ratio is 3.68%
    Totally cost:  10.928016424179077 s
    """
