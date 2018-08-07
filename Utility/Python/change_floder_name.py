import os

def getFloderList(path):
    if not path:
        raise ValueError('path cannot be empty.')
    return os.listdir(path)

def changeFloderName(path):
    floder_list = getFloderList(path)
    for floder in floder_list:
        p = os.path.join(path, floder)
        # # change name you want
        # my floder first character is duplicated.
        floder_old_name = floder[1:].replace('_', ' ')
        # change str to list so that can use insert.
        name_list = list(floder_old_name)
        # i want to insert '.' at index 3
        name_list.insert(3, '.')
        # new floder name
        floder_new_name = "".join(name_list)
        new_floder = os.path.join(path, floder_new_name)
        os.rename(p, new_floder)

def main():
    changeFloderName('F:/IDEA/Algorithms/leetcode')
    
if __name__ == '__main__':
    main()
