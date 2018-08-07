
def main():
	str = ' 123_asdf'
	newStr = str[1:].replace('_', ' ')
	strList = list(newStr)
	strList.insert(3, '.')
	newStr = "".join(strList)
	print(newStr)

if __name__ == '__main__':
	main()