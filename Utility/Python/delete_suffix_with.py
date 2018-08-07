import os


def get_file_list(path):
    if not path:
        raise ValueError('path cannot be empty.')
    return os.listdir(path)


def delete_suffix_with(suffix, path):
    file_list = get_file_list(path)
    for filename in file_list:
        p = os.path.join(path, filename)
        if os.path.isdir(p):
            delete_suffix_with(suffix, p)
        elif p.endswith(suffix):
            os.remove(p)


def main():
    path = input('input the path:')
    suffix = input('which files do you want to delete with what suffix?')
    delete_suffix_with(suffix, path)


if __name__ == '__main__':
    main()
