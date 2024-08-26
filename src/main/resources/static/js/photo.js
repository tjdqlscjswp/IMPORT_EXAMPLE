	 var imgArray= new Array();
        imgArray[0]="/images/packages/pack1.jpg";  //사진
        imgArray[1]="/images/packages/pack2.jpg"; //사진
        imgArray[2]="/images/packages/pack3.jpg";    //사진
        imgArray[3]="/images/packages/pack4.jpg";    //사진
        imgArray[4]="/images/packages/pack5.jpg";    //사진
        imgArray[5]="/images/packages/pack6.jpg";    //사진
 
        
        function showImage()
        {
            var imgNum=Math.round(Math.random()*6);
            var objImg=document.getElementById("introImg");
            objImg.src=imgArray[imgNum];
        }