find_java_files_cmd="find ./src -name *.java"
find_fxml_files_cmd="find ./src -name *.fxml"
main_class=App

javac \
 -encoding utf-8 \
 --module-path "D:\Libs\javafx-sdk-16\lib" \
 --add-modules "javafx.controls,javafx.fxml" \
 -sourcepath ./src \
 -d bin $($find_java_files_cmd)

echo Main-Class: $main_class > ./bin/MANIFEST.MF

for x in $($find_fxml_files_cmd)
do
	# path without ./src/
	new_x=${x:6}
	len_new_x=${#new_x}
	
	# extract file name + ext.
	ending=${new_x##*/}
	len_ending=${#ending}
	
	# length without ending
	let del=${len_new_x}-${len_ending}
	dest_path=bin/${new_x:0:$del}
	
	if [ ! -d $dest_path ]; then
		mkdir $dest_path
	fi
	
	cp $x $dest_path
done