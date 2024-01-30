

let SongsContainer=document.querySelector('.SongsContainer');
let inner=document.querySelector('.inner');
let Songssize=0;
let input =document.querySelector('.input')
//let audio=null;
//let loader =document.querySelector('.loader');
 //loader.style.display ='none';

console.log(SongsContainer);
  // Add a class to the <div> element

                
			
 let error=document.querySelector('.error');
 let searchContainer=document.querySelector('.search-btn');
 let btn=document.querySelector('.btn');
let ztml="";
let html="";
 let SongsObject=inner.getAttribute('Song');
 //console.log(SongsObject);
 let Songs=JSON.parse(SongsObject);
   console.log(Songs);
for(let i=0;i<Songs.length;i++){
	html+=`
	
   <div class="card">
   <div class="card-body">
     <p>${Songs[i].song}</p>
                <p>${Songs[i].artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${Songs[i].image}" alt="Song Image">
                 <audio controls class="audio" class="audios">
                    <source src="data:audio/mpeg;base64,${Songs[i].songFile}"  type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>

   `
   }
     SongsContainer.innerHTML=html;
     
     btn.addEventListener("click",()=>{
		  
		 let rtml=''
		
		   console.log(input.value);
		   console.log(Songs.length);
		   let res=filterSongs(input.value);
		   console.log("res",res);
		   
		  if(input.value==""  ){
			  
			   /*let p = document.createElement('p');
                p.classList.add('emp');
               error.appendChild(p);
               let emp= document.querySelector('.emp');*/
               
              
               error.style.display="block";

              			   
        return; 
        
           
			       
		  }
		 if( input.value!=null && res==false){
			 
			 error.style.display="none";
			 //existingError.parentNode.removeChild(existingError);
			        
			        rtml+=`<h2 >There are no songs to display</h2>`
			       
			        
			     	SongsContainer.innerHTML=rtml;
			     	
		
			 }
		
		  
	 })
     
    function filterSongs( value){
		ztml='';
	 const filteredSongs = Songs.filter(e => e.song.startsWith(value));
const Songssize = filteredSongs.length;

console.log(Songssize);

if (Songssize==0) {
     
	 return Songssize <0;
}
		 else if(Songssize>0){
			 
			 filteredSongs.forEach(e => {
			 console.log(e.song);
			ztml+=`
	
   <div class="card">
   <div class="card-body">
     <p>${e.song}</p>
                <p>${e.artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${e.image}" alt="Song Image">
                 <audio controls >
                    <source src="data:audio/mpeg;base64,${e.songFile}" class="audio" type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>

   `
		 })
		SongsContainer.innerHTML=ztml;
		
	}
	 return Songssize > 0;
	

}

  