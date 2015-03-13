// ==UserScript==
// @name        Test
// @namespace   http://github.com/elasticsearch/elasticsearch/pull
// @include     https://github.com/*/*/pull/*
// @require     http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js
// @version     1
// @grant       GM_xmlhttpRequest
// ==/UserScript==
$(document).ready(showAlert);






function showAlert() {
  var t =  (document.getElementsByClassName("tabnav-tabs")[0]).children[2].cloneNode(true);
  console.log (t);
 
  var related_bucket_content = document.getElementById("toc").cloneNode(true);
  related_bucket_content.setAttribute("id","toc_related_files");
  related_bucket_content.removeChild(related_bucket_content.children[0]);
  related_bucket_content.children[0].setAttribute("id","warning_label");
  related_bucket_content.children[1].setAttribute("id","file_list");
  
  var related_bucket = document.getElementById("files_bucket").cloneNode(false);
  related_bucket.setAttribute("id","related_bucket");
  related_bucket.appendChild(related_bucket_content);
  var parent_of_bucket = document.getElementsByClassName("view-pull-request clearfix js-issues-results")[0].appendChild(related_bucket);
  
  var repository = $(".js-current-repository").attr('href');
  
  
  t.children[0].setAttribute("data-container-id","related_bucket");
  var ih = t.children[0].innerHTML.replace("Files changed","Related Files");
  t.children[0].innerHTML = ih;
  document.getElementsByClassName("tabnav-tabs")[0].appendChild(t);
  
  
  var warning_label = document.getElementById("warning_label");
  warning_label.innerHTML = "You might want to review the following files for potential impact";
  warning_label.style = "background-color:#e8f0f8";
  document.getElementById("toc_related_files").setAttribute("class","details-collapse table-of-contents js-details-container open");
  
  var clone = document.getElementById("file_list").children[0].cloneNode(true);
  $("#file_list").empty();
  
  
  var imageUrl = 'http://i57.tinypic.com/aywti9.gif';
  
  $("#file_list").css('background-image','url(' + imageUrl + ')');
  
  
  
  
  
  var XChangeFile = new Object();
  var changesets = new Array();
  //changesets[0] = "check";
 
  $("#toc > ol > li").children("a").each( function(index) {
    changesets[index] = $( this ).text();
  })
  XChangeFile.changeSets = changesets;
  XChangeFile.repository = repository;
  //alert(changed_file);
  //alert(JSON.stringify(XChangeFile));
  
  GM_xmlhttpRequest({
    method: "POST",
    headers: {"Accept": "application/json"},
    data : JSON.stringify(XChangeFile),
    url: "http://localhost:8080/CS846_Web/rest/relatedfile",
    onload: function(response) {
      updateData(response,clone);
    }
  });
 
}

function updateData(response,clone) {
  //alert($().jquery);
  $("#file_list").css("background-image","none");
  var json_response = response.responseText;
  var response_object = JSON.parse(json_response);
  //alert(json_response);
 
  var data = new Array();
  data = response_object.relatedProductFiles;
  
  var data_test = new Array();
  data_test = response_object.relatedTestFiles;
 
 
  
  
  for ( var i = 0, l = data.length; i < l; i++ ) {
    var localclone = clone.cloneNode(true);
    document.getElementById("file_list").appendChild(localclone);
    $("#file_list").children().eq(i).children("a").text(data[i]);
  }
  
  
  for ( var j = 0, k = data.length, m = data_test.length; j < m; j++, k++) {
    var localclone = clone.cloneNode(true);
    document.getElementById("file_list").appendChild(localclone);
    $("#file_list").children().eq(k).css( "background-color", "PaleTurquoise").children("a").text(data_test[j]);
    
  }
 
  
 
}