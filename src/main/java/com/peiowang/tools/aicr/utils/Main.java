package com.peiowang.tools.aicr.utils;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        System.out.println(cache.get(2));       // 返回 -1 (未找到)
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        System.out.println(cache.get(1));       // 返回 -1 (未找到)
        System.out.println(cache.get(3));       // 返回  3
        System.out.println(cache.get(4));       // 返回  4

    }
    public static class LRUCache {
        Map<Integer,LRUCacheNode> map=new HashMap<>();
        int size;
        int count=0;
        LRUCacheNode head;
        LRUCacheNode tail;
        public LRUCache(int num){
            size=num;
            head=new LRUCacheNode();
            tail=new LRUCacheNode();
            head.next=tail;
            tail.pre=head;
        }
        public void put(int key,int value){
            if(map.containsKey(key)){
                LRUCacheNode node=map.get(key);
                node.value=value;
                LRUCacheNode pre=node.pre;
                LRUCacheNode next=node.next;
                pre.next=next;
                next.pre=pre;
                LRUCacheNode headNext=head.next;
                headNext.pre=node;
                head.next=node;
                node.pre=head;
                node.next=headNext;
            }else{
                LRUCacheNode node=new LRUCacheNode();
                node.key=key;
                node.value=value;
                LRUCacheNode headNext=head.next;
                headNext.pre=node;
                head.next=node;
                node.pre=head;
                node.next=headNext;
                count++;
                //TODO移除节点
                if(count>size){
                    remove();
                }
            }
        }
        public Integer get(int key){
            if(!map.containsKey(key)){
                return -1;
            }else{
                LRUCacheNode node=map.get(key);
                int value=node.value;
                LRUCacheNode pre=node.pre;
                LRUCacheNode next=node.next;
                pre.next=next;
                next.pre=pre;
                LRUCacheNode headNext=head.next;
                headNext.pre=node;
                head.next=node;
                node.pre=head;
                node.next=headNext;
                return value;
            }
        }
        public void remove(){
            LRUCacheNode node=tail.pre;
            LRUCacheNode pre=node.pre;
            pre.next=tail;
            tail.pre=pre;
            map.remove(node);
            count--;
        }
    }
    public static class LRUCacheNode {
        public int key;
        public int value;
        public LRUCacheNode pre;
        public LRUCacheNode next;

    }
}
