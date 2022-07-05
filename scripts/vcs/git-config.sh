#!/bin/bash

root_path=$(git rev-parse --show-toplevel)
git_config_path="$root_path/.git/config"
git_commit_template_path="$root_path/scripts/templates/git-commit"

if grep -zqE "[[commit]][[:space:]]*template" $git_config_path
then
	echo "Git commit template already defined for repository."
	while true; do
    	read -p "Replace with default [y/n]? " yn
    	case $yn in
        	[Yy]* ) 
				# --expression used to replace '/' with '@' to be able to use the '/' in the template path
				sed -i --expression "/^ *\[commit\]/,/^ *[^:]*:/s@template.*@template = ${git_commit_template_path}@" $git_config_path
				break;;
        	[Nn]* ) echo "Aborting"; exit;;
        	* ) echo "Please answer y or n.";;
    	esac
	done
elif grep -zqE "[[commit]]" $git_config_path
then
	# [commit] block found
	# --expression used to replace '/' with '@' to be able to use the '/' in the template path
	sed -i --expression "s@\[commit\]@\[commit\]\n\ttemplate = ${git_commit_template_path}@" $git_config_path
else
	# [commit] block not found. Add it with template
	echo "[commit]" >> $git_config_path
	echo "	template = ${git_commit_template_path}" >> $git_config_path 
fi

echo "$git_commit_template_path set as a commit template for this repository"
