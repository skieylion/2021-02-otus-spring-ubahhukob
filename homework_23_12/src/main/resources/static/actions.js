function find(){
    let formData = new FormData(myForm2);
    axios.get("/book/"+formData.get("id"))
    .then((response) => {
        viewBook(response.data);
    });

    return false;
}
function update(){
    let formData = new FormData(myForm3);
    axios.put("/book",{
        id: formData.get("id"),
        name: formData.get("name")
    }).then((response) => {
        axios.get("/book/"+formData.get("id"))
        .then((response) => {
            viewBook(response.data);
        });
    });

    return false;
}
function create(){
    let formData = new FormData(myForm4);
    axios.post("/book",{
        name:formData.get("name"),
        author:{
            fullName:formData.get("author")
        },
        genre:{
            name:formData.get("genre")
        },
        comments:[{
                description:formData.get("comment")
        }]
    }).then((response) => {
        axios.get("/book/"+response.data)
        .then((response2) => {
            viewBook(response2.data);
        });
    });

    return false;
}
function deleteBook(){
    let formData = new FormData(myForm5);
    axios.delete("/book/"+formData.get("id"))
    .then((response) => {
        axios.get("/book")
        .then((response2) => {
            viewListBook(response2.data);
        });
    });

    return false;
}
function findAll(){
    console.log(123);
    axios.get("/book")
    .then((response) => {
        viewListBook(response.data);
    });
}
findAll();