package io.github.flauberjp.forms.model;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class GitDirListRenderer extends JCheckBox implements
ListCellRenderer<GitDir> {
	 
@Override
public Component getListCellRendererComponent(
   JList<? extends GitDir> list, GitDir value,
   int index, boolean isSelected, boolean cellHasFocus) {
setEnabled(list.isEnabled());
setSelected(value.isSelected());
setFont(list.getFont());
setBackground(list.getBackground());
setForeground(list.getForeground());
setText(value.toString());
return this;
}
}
