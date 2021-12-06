<style>
    <%@include file='../css/styles.css' %>
</style>
<html>
<head>
    <title>Chat Room</title>
</head>
<body>
<h2>Chat Room</h2>
<div class="start">
    <input type="text" class="username" placeholder="Please enter your name">
    <button id="start">Enter Chat Room</button>
</div>
<div class="chatbox">
    <textarea class="msg"></textarea>
    <div class="messages"></div>
</div>
</body>
<script>
    let chatUnit = {
        init(){
            this.startbox = document.querySelector(".start");
            this.chatbox = document.querySelector(".chatbox");
            this.startBtn = this.startbox.querySelector("button");
            this.nameInput = this.startbox.querySelector("input");
            this.msgTextArea = this.chatbox.querySelector("textarea");
            this.chatMessageContainer = this.chatbox.querySelector(".messages");
            this.bindEvents();
        },

        bindEvents(){
            this.startBtn.addEventListener("click", e=> this.openSocket());
            this.nameInput.addEventListener("keyup", e=> {
                if (e.keyCode === 13) {
                    this.openSocket()
                }
            });
            this.msgTextArea.addEventListener("keyup", e=>{
                if(e.keyCode === 13) {
                    e.preventDefault();
                    this.send();
                }
            });
        },

        send(){
            this.sendMessage({
                name:this.name,
                text:this.msgTextArea.value
            });
        },

        onOpenSock(){

        },
        onMessage(msg) {
            let msgBlock = document.createElement("div");
            msgBlock.className = "msg";
            let fromBlock = document.createElement("div");
            fromBlock.className = "from";
            fromBlock.innerText = msg.name;

            let textBlock = document.createElement("div");
            textBlock.className = "text";
            textBlock.innerText = msg.text;

            msgBlock.appendChild(fromBlock);
            msgBlock.appendChild(textBlock);
            this.chatMessageContainer.prepend(msgBlock);
        },

        onClose(){

        },
        sendMessage(msg){
            this.onMessage({name:"Me:", text: msg.text})
            this.msgTextArea.value="";
            this.ws.send(JSON.stringify(msg));
        },
        openSocket() {
            this.name = this.nameInput.value;
            this.ws = new WebSocket("ws://localhost:8080/chat/" + this.name + ":");
            this.ws.onopen = () => this.onOpenSock();
            this.ws.onmessage = (e) => this.onMessage(JSON.parse(e.data));
            // this.ws.onclose = () => this.onClose();
            this.startbox.style.display = "none";
            this.chatbox.style.display = "block";
        }
    };

    window.addEventListener("load", e=>chatUnit.init());

</script>
</html>