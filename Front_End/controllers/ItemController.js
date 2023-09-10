$(document).ready(function() {
    getAllItem();
});

    function getAllItem() {
    $("#tblItem").empty();
    $.ajax({
    url:"http://localhost:8080/Back_End1/pages/item",

    success:function (item){
    for (let j in item) {
    let items = item[j];
    let code = items.code;
    let description = items.description;
    let qty = items.qtyOnHand;
    let unitPrice = items.unitPrice;


    console.log(code,description,qty,unitPrice);

    let row1 = `<tr><th>${code}</th><th>${description}</th><th>${qty}</th><th>${unitPrice}</th></tr>`;
    $("#tblItem").append(row1);
}

    bindRowClickOnAction();
    clearRow("","","");
}

});
}

    $("#btnGetAllItem").click(function (){
    getAllItem();
});

    $("#btnItem").click(function (){
    let formData = $("#itemForm").serialize();
    $.ajax({
    url: "http://localhost:8080/Back_End1/pages/item",
    method:"post",
    data : formData,
    dataType:"json",
    success:function (res){
    alert(res.message);
    getAllItem();
},
    error:function (error){
    alert(JSON.parse(error.responseText).message);
}
});
});

    $("#btnItemUpdate").click(function (){
    let itemCode = $("#itemCode").val();
    let itemName = $("#itemName").val();
    let itemQty = $("#itemQty").val();
    let itemUnitPrice =$("#itemPrice").val();


    var itemDB = {
    code : itemCode,
    description:itemName,
    qtyOnHand:itemQty,
    unitPrice:itemUnitPrice
}

    $.ajax({
    url:"http://localhost:8080/Back_End1/pages/item",
    method: "put",
    data: JSON.stringify(itemDB),
    contentType:"application/json",


    success: function (res){
    getAllItem();
    alert(res.message);
}
});

});

    //Remove BTN
    $("#btnItemDelete").click(function (){

    let code = $("#itemCode").val();

    $.ajax({
    url : "http://localhost:8080/Back_End1/pages/item?code="+code,
    method :"delete",
    success : function (res){
    getAllItem();
    alert(res.message);
}
});


});



    //clear BTN
    $("#btn-clear").click(function () {
    clearRow("","","","");
});

    //Data Bind with Text Fields
    function bindRowClickOnAction() {
    $("#tblItem>tr").click(function () {

        let code = $(this).children(":eq(0)").text();
        let description = $(this).children(":eq(1)").text();
        let qty = $(this).children(":eq(2)").text();
        let price = $(this).children(":eq(3)").text();

        $("#itemCode").val(code);
        $("#itemName").val(description);
        $("#itemQty").val(qty);
        $("#itemPrice").val(price);

    });

}

    //Clear Text Fields
    function clearRow(code,description,qty,price){


    $("#itemCode").val(code);
    $("#itemName").val(description);
    $("#itemQty").val(qty);
    $("#itemPrice").val(price);

}
