#!/usr/bin/python
# coding=utf-8

import os
import re
import sys

def commentLog(path):
	lines = []
	isCommented = False
	fout = open(path, "r")
	for line in fout.readlines():
		if re.match("\s*L\..*(.*);", line) or re.match("import wxc\.android\.logwriter\.L;", line):
			line = "// " + line
			isCommented = True
		lines.append(line)
	fout.close()

	if isCommented:
		fin = open(path, "w")
		fin.writelines(lines);
		fin.close()

def uncommentLog(path):
	lines = []
	hasComment = False
	fout = open(path, "r")
	for line in fout.readlines():
		if re.match("\s*//\s*L\..*(.*);", line) or re.match("\s*//\s*import wxc\.android\.logwriter\.L;", line):
			index = line.index("// ") + 3
			line = line[index:]
			hasComment = True
		lines.append(line)
	fout.close()

	if hasComment:
		fin = open(path, "w")
		fin.writelines(lines);
		fin.close()

if __name__ == "__main__":
	if len(sys.argv) == 2:
		mode = sys.argv[1]
		# print mode, type(mode)
		# print sys.path[0]
		os.chdir(sys.path[0])
		try:
			if mode == '0':
				os.rename("app/libs/llogger.jar", "llogger.jar") 
			else:
				os.rename("llogger.jar", "app/libs/llogger.jar") 
		except OSError:
			pass
			
		for root, dirs, files in os.walk("app/src/main/java"):
			if len(files) > 0:
				for f in files:
					if f.endswith(".java"):
						path = root + '/' + f
						if mode == '0':
							commentLog(path)
						else:
							uncommentLog(path)
	else:
		print "Please set mode, 0:coment Log, 1:uncomment Log"