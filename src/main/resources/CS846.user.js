// ==UserScript==
// @name        RelatedFile
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
  /*
  var ih = t.children[0].innerHTML.replace("Files changed","Related Files");
  t.children[0].innerHTML = ih;
  */
  var ih = t.innerHTML.replace("Files changed","Related Files");
  t.innerHTML = ih;
  document.getElementsByClassName("tabnav-tabs")[0].appendChild(t);
  
  $(".tabnav-tabs").children().eq(3).attr("data-container-id","related_bucket");  
  
  //$(".tabnav-tabs").children().eq(3).children().eq(0).replaceWith("<span data-container-id=\"related_bucket\" class=\"octicon octicon-diff\"></span>Files changed<span id=\"files_tab_counter\" class=\"counter\"></span>");
  
  //(".tabnav-tabs").children().eq(3).html("<span data-container-id=\"related_bucket\" class=\"octicon octicon-diff\"></span>Files changed<span id=\"files_tab_counter\" class=\"counter\"></span>");
  //var temp = $(".tabnav-tabs").children().eq(3).children("span").eq(1).next().html();
  //alert(temp);
  
  var warning_label = document.getElementById("warning_label");
  warning_label.innerHTML = "You might want to review the following files for potential impact";
  warning_label.style = "background-color:#e8f0f8";
  document.getElementById("toc_related_files").setAttribute("class","details-collapse table-of-contents js-details-container open");
  
  var clone = document.getElementById("file_list").children[0].cloneNode(true);
  $("#file_list").empty();
  
  
  (document.getElementsByClassName('counter')[3]).innerHTML = "";
  
  
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
  XChangeFile.mode = "evaluation";
  
  var commit_hash = $(".commit-group.table-list.table-list-bordered").children("li").eq(0).attr("data-url");
  //alert(commit_hash);
  XChangeFile.commitHash = commit_hash;
  
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
 
 
  
  var i = 0;
  
  //alert("Starting product");
  
  for ( var l = data.length; i < l; i++ ) {
    //alert(i);
    //alert(data[i].file);
    var f = "f";
    var localclone = clone.cloneNode(true);
    //var origtext = localclone.childNodes[0];
    //alert(origtext.innerHTML);
    document.getElementById("file_list").appendChild(localclone);
    $("#file_list").children("li").eq(i).children("a").text(data[i].file);
    
    $("#file_list").children("li").eq(i).children("a").click( function() { 
                                                                      var details_div = $(this).parent().next();
                                                                      if(details_div.css('display') == 'none'){ 
                                                                         details_div.css("display", "block");
                                                                      } else {
                                                                         details_div.css("display", "none");
                                                                      }
                                                                      
                                                                     
                                                                     } );
    
    var div1 = document.createElement("div");
    var txt3 = document.createElement("p");  
    txt3.innerHTML = data[i].message; 
    div1.appendChild(txt3);
    //div1.style.border = "5px double #C0C0C0";
    div1.style.display = "none";
    div1.id = f.concat(i);
    document.getElementById("file_list").appendChild(div1);
    
    $("#file_list").children("li").eq(i).children("span").eq(0).html(data[i].confidence);
    var msg = data[i].message
    $("#file_list").children("li").eq(i).children("a").attr('title', msg);
    
  }
  
 
  /*
  for ( var j = 0, k = i, m = data_test.length; j < m; j++, k++) {
    var localclone = clone.cloneNode(true);
    document.getElementById("file_list").appendChild(localclone);
    $("#file_list").children().eq(k).css( "background-color", "PaleTurquoise").children("a").text(data_test[j]);
    
    
    var div1 = document.createElement("div");
    var txt3 = document.createElement("p");  
    txt3.innerHTML = data_test[j] + data_test[j] + data_test[j] + data_test[j]; 
    div1.appendChild(txt3);
    div1.style.border = "5px double #C0C0C0";
    div1.style.display = "none";
    document.getElementById("file_list").appendChild(div1);
    
    k++;
    
  }
  */
  //alert("Starting test");
  //alert(data_test.length+data.length);
  for ( var l = data_test.length+data.length; i < l; i++ ) {
    
    var f = "f";
    var localclone = clone.cloneNode(true);
    document.getElementById("file_list").appendChild(localclone);
    
    $("#file_list").children("li").eq(i).css( "background-color", "PaleTurquoise");
    
    $("#file_list").children("li").eq(i).children("a").text(data_test[l-i-1].file);
    $("#file_list").children("li").eq(i).children("a").addClass('message');
    
    
    $("#file_list").children("li").eq(i).children("a").click( function() { 
                                                                      var details_div = $(this).parent().next();
                                                                      if(details_div.css('display') == 'none'){ 
                                                                         details_div.css("display", "block");
                                                                      } else {
                                                                         details_div.css("display", "none");
                                                                      }
                                                                      
                                                                     
                                                                     } );
    
    var div1 = document.createElement("div");
    var txt3 = document.createElement("p");  
    txt3.innerHTML = data_test[l-i-1].message; 
    div1.appendChild(txt3);
    //div1.style.border = "5px double #C0C0C0";
    div1.style.display = "none";
    div1.id = f.concat(i);
    document.getElementById("file_list").appendChild(div1);
    $("#file_list").children("li").eq(i).children("span").eq(0).html(data_test[l-i-1].confidence);
    var msg = data_test[l-i-1].message
    $("#file_list").children("li").eq(i).children("a").attr('title', msg);
    
  }
  
 
}