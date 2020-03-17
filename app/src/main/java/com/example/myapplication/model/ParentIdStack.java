package com.example.myapplication.model;

public class ParentIdStack {
    private int[] parentId;
    private int elementSize;

    public ParentIdStack(int capacity) {
        parentId = new int[capacity];
    }

    ParentIdStack() {
        parentId = new int[99];
    }

    public void push(int value)//进栈
    {
        if (elementSize > parentId.length) {
            int[] temp = new int[parentId.length * 2];
            System.arraycopy(parentId, 0, temp, 0, parentId.length);
            parentId = temp;
        }
        parentId[elementSize++] = value;
    }

    public int pop()//出栈，出栈前必须调用empty（）判断栈是否为空
    {
        return parentId[--elementSize];
    }

    public boolean empty()//判断栈是否为空
    {
        return elementSize == 0;
    }

    public int getSize()//获取栈中元素个数
    {
        return elementSize;
    }

    public int peek()//获取当前栈顶元素
    {
        return parentId[elementSize - 1];
    }

}
