package com.vuck.enums;
import com.vuck.annotations.Add;
import com.vuck.annotations.Delete;
import com.vuck.annotations.Find;
import com.vuck.annotations.SaveOrUpdate;

public enum Annotation {
	ADD(Add.class), FIND(Find.class), DELETE(Delete.class), UPDATE(SaveOrUpdate.class);
	private Annotation(Object obj) {
	}
}
