


let SongsContainer=document.querySelector('.SongsContainer');

let Songssize=0;
let inner =document.querySelector('.inner')
let profileImage =document.querySelector('.profileImg');
let input =document.querySelector('.input')
let user_img =document.querySelector('.user_img');
let submit_btn=document.querySelector('.btn')
let Accountdetails=document.querySelector('.Account_details');
 let modal_body=document.querySelector('.modal-body-h3');
 let error=document.querySelector('.error');
 let searchContainer=document.querySelector('.search-btn');
 
let prev=document.querySelector('.prev');
let next=document.querySelector('.next');
var Img_data="";
 let pagesize=3;
 let page=0;
 
 const Userapi="http://localhost:8080/UserImage";
  
 const getImageapi="http://localhost:8080/ProfileImage";
    
 var SongsObject=inner.getAttribute('Song');
  var Songs=JSON.parse(SongsObject);
  console.log(Songs);

 
console.log("hiiii"+profileImage);
user_img.addEventListener('mouseover',()=>{
    
	Accountdetails.style.display='block';
	
})



function openFileDialog() {
    const fileInput = document.getElementById('fileInput');
    
   
    fileInput.click();
}

function handleFileSelect(event) {
	 
	    
    const file = event.target.files[0];
     
     const blob = new Blob([file],{ type: 'image/webp' });
     
	
	
	





    uploadImage(file);
}
function uploadImage(file){
  
  

    const formData = new FormData();
    formData.append('image', file);
    
    fetch(Userapi, {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
		localStorage.removeItem('profileImageData')
        localStorage.setItem('profileImage', data);
        console.log(user_img.src = `data:image/jpeg;base64,${data}`);
         user_img.src = `data:image/jpeg;base64,${data}`
            getprofileImage(); // Handle success response
    })
    .catch(error => {
        console.error('There was a problem with the upload:', error);
    });
      
	
	
	   
	   //console.log("in get profile"+getprofileImage());
		
    
    
        
     
}



    
function getprofileImage() {
    fetch(getImageapi, {
        method: 'GET',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
        console.log("get profile data");
        Img_data = data;
        console.log(user_img.src = `data:image/jpeg;base64,${data}`);

        user_img.src = `data:image/jpeg;base64,${data}`;
        localStorage.setItem('profileImageData', Img_data);
    })
    .catch(error => {
        console.error('There was a problem with the upload:', error);
    });
}

function displayCachedProfileImage() {
    const cachedImageData = localStorage.getItem('profileImageData');
    const updatedImageData=localStorage.getItem('profileImage')
    if (cachedImageData) {
        user_img.src = `data:image/jpeg;base64,${cachedImageData}`;
        return true;
    }
       else if(updatedImageData){
		    user_img.src = `data:image/jpeg;base64,${updatedImageData}`;
        return true;
	   }
     else {
        return false;
    }
}

// Fetch profile image if not already cached
if (!displayCachedProfileImage()&& !uploadImage) {
    getprofileImage();
}
Accountdetails.addEventListener('mouseover', () => {
    
    Accountdetails.style.display = 'block';
});

Accountdetails.addEventListener('mouseout', () => {
    
    Accountdetails.style.display = 'none';
});


			

let ztml="";
let html="";
 


 
for(let i=0;i<Songs.length;i++){
	html+=`
	
   <div class="card">
   <div class="card-body">
     <p>${Songs[i].song}</p>
                <p>${Songs[i].artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${Songs[i].image}" alt="Song Image">
                 <audio controls class="audio" preload="metadata">
                    <source src="data:audio/mpeg;base64,${Songs[i].songFile}"  type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>

   `
   }
     SongsContainer.innerHTML=html;
     
     
    var SongElements=SongsContainer.getElementsByClassName('audio');
    
   
     for(let i=0;i<SongElements.length;i++){
		
		 
		  let Song=SongElements[i];
		  
		     
		 Song.addEventListener('play',()=>{
			 
			
			 
		
			 
			 for(let j=0;j<SongElements.length;j++){
				 
			 if(SongElements[j]!==Song && !SongElements[j].paused){
				
				SongElements[j].pause();
				
				
				}

			}
			

			 
		 })
		    

			
	 }
	 
			 
     
  
     
     
     input.addEventListener('input',()=>{
		 error.style.display="none";
	 })
     
     submit_btn.addEventListener("click",()=>{
		 
		 //const regex=/^[a-zA-Z\s]+$/;
		 	  
		 
		  if(input.value=="" ||input.value.length==0){
			  
			  
                 
                 modal_body.innerHTML="please enter a valid value ";
              submit_btn.setAttribute('data-toggle', 'modal');
                submit_btn.setAttribute('data-target', '#exampleModal');

              			   
        return; 
        
           
			       
		  }
		  
		    else if (input.value!=="" || input.vlaue.length>0){
				
			    filterSongs(input.value);
				
				
			}
		  
		
	
		  
	 })
     
    function filterSongs( value){
		ztml='';
	 const filteredSongs = Songs.filter(e => e.song.toLowerCase().startsWith(value.toLowerCase()));
     const Songssize = filteredSongs.length;



if (Songssize==0) {
	
	  
	  
	   submit_btn.setAttribute('data-toggle', 'modal');
       submit_btn.setAttribute('data-target', '#exampleModal');
       
       modal_body.innerHTML=`No Songs Found with ${input.value}`
      return true;
}
		 else if(Songssize>0){
			 
			   submit_btn.removeAttribute('data-toggle', 'modal');
			        modal_body.innerHTML="";
			 filteredSongs.forEach(e => {
		
			ztml+=`
	
   <div class="card">
   <div class="card-body">
     <p>${e.song}</p>
                <p>${e.artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${e.image}" alt="Song Image">
                 <audio controls class="audio" preload="metadata">
                    <source src="data:audio/mpeg;base64,${e.songFile}"  type="audio/mpeg">
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
	function fetchDataAndUpdateUI(page){
		
		if(page<0){
			prev.disabled=true;
			return;
		}
		
		let z="";
		const params = {
  page: page,
  size: pagesize
};

// Convert the parameters to a query string
const queryString = Object.keys(params)
  .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(params[key]))
  .join('&');

// Specify the URL with the query string
const url = 'http://localhost:8082/songs?' + queryString


      

		fetch(url).then(response=>response.json()).then(data=>{
			console.log(data[0]);
			
			 if (data.length ===0 ) {
                next.disabled=true; 
                return;
               
            }
			
			for(let i=0;i<data.length;i++){
			z+=` <div class="card">
   <div class="card-body">
     <p>${data[i].song}</p>
                <p>${data[i].artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${data[i].image}" alt="Song Image">
                 <audio controls class="audio "preload="metadata">
                    <source src="data:audio/mpeg;base64,${data[i].songFile}"  type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>`
			
			}
			
             
                next.disabled = false;
                prev.disabled=false;
                	SongsContainer.innerHTML=z; // Enable the button if there are more elements to display
            
        })
        .catch(error => {
            console.error('Error occurred during fetch:', error);
           
       
		})
		
		  
		
		 
	}

   next.addEventListener("click",()=>{
	    page = page + 1;
	   fetchDataAndUpdateUI(page)
	  
   })

    prev.addEventListener("click",()=>{
		 page = page - 1;
	   fetchDataAndUpdateUI(page)
	  
   })
  