
    $(document).ready(function() {
    loadItemCode();
    loadCusCode();


});

    var genarateOrderID = 0;


    $("#txtOrderID").val('0-00' + (genarateOrderID += 1));

    function loadItemCode() {
    $("#selectItemCode").empty();
    // let code = $("#selectItemCode").val();
    $.ajax({
    url: 'http://localhost:8080/Back_End1/pages/item',
    success: function (res) {
     for (let item of res.data) {

    let id = `<option value="${item.code}">${item.code} ${item.description}</option>`;
    $("#selectItemCode").append(id);
}
},
    error: function (error) {
    alert(JSON.parse(error.responseText).message);
}
});

}

    $("#selectItemCode").change(function ()   {
    let code = $("#selectItemCode").val();
    $("txtOrderID").val(code);
    let res = searchItem(code);
    if (res.length > 0 ){
    $("#txtItemCode").val(res[0].code);
    $("#txtItemDescription").val(res[0].description);
    $("#txtItemPrice").val(res[0].unitPrice);
    $("#txtQTYOnHand").val(res[0].qtyOnHand);
}
}
    );

    function searchItem(code) {
    let response = "";
    $.ajax({
    url : "http://localhost:8080/Back_End1/pages/item",
    dataType: "json",
    async:false,

    success:function (resp){
    response = resp.data.filter((c) => {
    return c.code == code;
});
}
});
    return response;
}



    function loadCusCode() {
    $("#selectCusID").empty();
    // let id = $("#selectCusID").val();
    $.ajax({
    url: 'http://localhost:8080/Back_End1/pages/customer',
    dataType: "json",
    success: function (res) {
    console.log(res);
    for (let cus of res.data) {
    console.log(cus.id);

    $("#selectCusID").append(`<option value="${cus.id}">${cus.id} ${cus.name}</option>`);
}
},
    error: function (error) {
    alert(JSON.parse(error.responseText).message);
}
});
}
    function searchCustomer(cusID) {
    let response = "";
    $.ajax({
    url : "http://localhost:8080/Back_End1/pages/customer",
    dataType: "json",
    async:false,

    success:function (resp){
    response = resp.data.filter((c) => {
    return c.id == cusID;
});
}
});
    return response;
}



    $("#selectCusID").change(function ()  {
    let cusID = $("#selectCusID").val();
    $("orderCustomerID").val(cusID);
    let res = searchCustomer(cusID);
    if (res.length > 0 ){

    $("#orderCustomerID").val(res[0].id);
    $("#orderCustomerName").val(res[0].name);
    $("#orderCustomerAddress").val(res[0].address);
    $("#orderCustomerSalary").val("N/A");

}

});


    const currentDate = new Date();

    // Format the current date as yyyy-MM-dd
    const formattedDate = formatDate(currentDate);

    $('#txtDate').val(formattedDate);

    function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

    let orderItems= [];

    $("#btnAddToTable").click(function() {
    let itemCode = $("#txtItemCode").val();
    let itemName = $("#txtItemDescription").val();
    let price = parseFloat($("#txtItemPrice").val());
    let quantity = parseInt($("#txtQty").val());

    let total = price * quantity;

    orderItems.push({
    code: itemCode,
    description: itemName,
    unitPrice: price,
    quantity: quantity,
    total: total
});

    $("#orderCustomerID").val("");
    $("#orderCustomerName").val("");
    $("#orderCustomerAddress").val("");
    $("#txtItemCode").val("");
    $("#txtItemDescription").val("");
    $("#txtItemPrice").val("");
    $("#txtQTYOnHand").val("");
    $("#txtQty").val("");

    updateOrderTable();

    updateTotals();
});


    function updateOrderTable() {
    let tableBody = $("#orderTable");
    tableBody.empty();

    for (let item of orderItems) {
    let row = `<tr>
            <td>${item.code}</td>
            <td>${item.description}</td>
            <td>${item.unitPrice}</td>
            <td>${item.quantity}</td>
            <td>${item.total}</td>
        </tr>`;
    tableBody.append(row);
}
}

    function updateTotals() {
    let totalAmount = 0;

    for (let item of orderItems) {
    totalAmount += item.total;
}

    let subtotalAmount = totalAmount;
    let cashAmount = parseFloat($("#txtCash").val()) || 0;
    let discountAmount = parseFloat($("#txtDiscount").val()) || 0;

    let balanceAmount = cashAmount - discountAmount - subtotalAmount;

    $("#total").text(totalAmount.toFixed(2));
    $("#subtotal").text(subtotalAmount.toFixed(2));
    $("#txtBalance").val(balanceAmount.toFixed(2));
}

    let disTOGave = 0;

    $('#txtDiscount').on('keyup', function() {
    let dis = parseFloat($('#txtDiscount').val());
    let tot = parseFloat($('#txtBalance').val());

    console.log(dis + "==" + tot);

    let totMin = tot * (dis / 100);
    console.log("Discount Amount: " + totMin);

    let subTot = tot - totMin;
    disTOGave = totMin;

    $('#subtotal').val(subTot);
    updateTotals();
});


    $("#btnSubmitOrder").click(function () {
    let oid = $("#txtOrderID").val();
    let date=   $("#txtDate").val();
    let odCusId=  $("#orderCustomerID").val();

    let odDetail=orderDetails;

    let or={
    "oid":oid,
    "date":date,
    "odCusId":odCusId,
    "odDetail":odDetail
}

    $.ajax({
    url:"http://localhost:8080/Back_End1/pages/purchase-order",
    method: "post",
    data: JSON.stringify(or),
    contentType: "application/json",
    dataType:"json",

    success: function (res) {
    alert(res.message);

},
    error: function (error) {
    alert(JSON.parse(error.responseText).message);
}
});
});


