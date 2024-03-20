package code.uica.code;

//UICA - Uni-graphic Interface Component Access
public enum UICAState {
	CREATE,
	READ, ////make readable ui inputs, select-boxes etc. Disallow to click them or input text
	EDIT, //make editable ui inputs, select-boxes etc. Show buttons and allow to click them
	HIDDEN;//Hide buttons and disallow to click them
}