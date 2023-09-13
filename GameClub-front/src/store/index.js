import {defineStore} from 'pinia';

export const mainStore = defineStore('main',{
    state:()=>{
        return{
            msg: 'Hello World!',
            userInfo:{
                username:'',
                token:'',
                id:''
            },
            user:{}
        }
    },
    getters:{
        getToken(){
            return this.userInfo.token;
        },
        getUserName(){
            return this.userInfo.username;
        },
        getUserId(){
            return this.userInfo.id;
        },
        getUser(){
            return this.user;
        }
    },
    actions:{
        changeToken(token){
            this.userInfo.token=token;
        },
        changeUserName(username){
            this.userInfo.username=username;
        },
        changeUserId(id){
            this.userInfo.id=id;
        },
        changeUser(user){
            this.user=user;
        }
    }
})