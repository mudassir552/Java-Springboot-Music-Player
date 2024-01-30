let inner=document.querySelector('.inner');
let SongsContainer=document.querySelector('.SongsContainer');
let innerContainer=document.querySelector('.innerContainer');
let input =document.querySelector('.inner');

let audio=null;
let loader =document.querySelector('.loader');
 loader.style.display ='none';

let html="";
 let SongsObject=inner.getAttribute('Song');
 let Songs=JSON.parse(SongsObject);

for(let i=0;i<Songs.length;i++){
	html+=`
	
   <div class="card" >
  <div class="card-body"  >
   
     <p>${Songs[i].song}</p>
                <p>${Songs[i].artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${Songs[i].image}" alt="Song Image">
                 <audio controls class="audio">
                    <source src="data:audio/mpeg;base64,${Songs[i].image}"  type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>

   `
   
   
   }
  setTimeout(function() {
  
     //SongsContainer.innerHTML=html;
     console.log("from timesetout")
      SongsContainer.innerHTML=html;

}, 100);
  

   if(SongsContainer.classList.contains("card")){
	     loader.style.display = 'none';
   }
   else{
	      loader.style.display = 'block';
   }
   
    setTimeout(function() {
  
     //SongsContainer.innerHTML=html;

     
    // Your code here
    audio = document.querySelectorAll(".audio");
    
      console.log("from dom content")
            audio.forEach(S =>{
			S.addEventListener('play',()=>{
				
				audio.forEach(other=>{
					if(other!==S){
						other.pause();
					
					}
				});
			});
		});
},200);
   

  
      
        
        

	  
      
     
	  