Vue.component("table-component",{
  template: '<table '+
                 ' id="tbl"'+
                 ' data-role="table"'+
                 ' class="table compact table-border cell-border"'+
                 ' data-show-pagination="false"'+
                 ' data-show-table-info="false"'+
                 ' data-show-search="false"'+
                 ' data-pagination-short-mode="false"'+
                 ' data-show-rows-steps="false"'+
                 ' data-show-activity="false"'+
                 ' data-static-view="true"'+
                 ' data-mute-table="false" >'+
             '>'+
                 '<thead>'+
                     '<tr>'+
                         '<th>Id</th>'+
                         '<th>Book</th>'+
                         '<th>Author</th>'+
                         '<th>Genre</th>'+
                     '</tr>'+
                 '</thead>'+
             '</table>'
})

new Vue({
  el: '#books'
});

function viewBook(data){
    var rows = [
        [data.id,data.name, data.author.fullName,data.genre.name],
    ];
    resetBook(rows);
}

function viewListBook(data){
    var rows = [];
    data.forEach(function(item,index){
        rows.push([item.id,item.name, item.author.fullName,item.genre.name]);
    });
    resetBook(rows);
}

function resetBook(rows){
    var table = Metro.getPlugin('#tbl', 'table');
    table.setItems(rows);
    table.draw();
}