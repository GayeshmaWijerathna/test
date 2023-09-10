$(document).ready(function () {
    getAllCustomers();
});


//Get All Method
function getAllCustomers() {
    $("#tblCustomer").empty();
    $.ajax({
        url: "http://localhost:8080/Back_End1/pages/customer",

        success: function (response) {
            let customer = response.data;
            for (let i in customer) {

                let cus = customer[i];
                let id = cus.id;
                let name = cus.name;
                let address = cus.address;

                console.log(id, name, address);

                let row = `<tr><td>${id}</td><td>${name}</td><td>${address}</td></tr>`;
                $("#tblCustomer").append(row);
            }
            bindRowClickOnAction();
            clearRow("", "", "");
        }

    });
}

//Get All BTN
$("#btnGetAll").click(function () {
    getAllCustomers();
});

//Save BTN
$("#btnSaveCustomer").click(function () {
    let formData = $("#customerForm").serialize();
    $.ajax({
        url: "http://localhost:8080/Back_End1/pages/customer",
        method: "post",
        data: formData,
        dataType: "json",
        success: function (res) {
            alert(res.message);
            getAllCustomers();
        },
        error: function (error) {
            alert(JSON.parse(error.responseText).message);
        }

    });
});

//Remove BTN
$("#btnCusDelete").click(function () {

    let id = $("#txtCustomerID").val();

    $.ajax({
        url: "http://localhost:8080/Back_End1/pages/customer?id=" + id,
        method: "delete",
        success: function (res) {
            getAllCustomers();
            alert(res.message);
        }
    });


});

//Update BTN
$("#btnUpdateCus").click(function () {

    let cusID = $("#txtCustomerID").val();
    let cusName = $("#txtCustomerName").val();
    let cusAddress = $("#txtCustomerAddress").val();

    var customerDB = {
        id: cusID,
        name: cusName,
        address: cusAddress
    }
    $.ajax({
        url: "http://localhost:8080/Back_End1/pages/customer",
        method: "put",
        data: JSON.stringify(customerDB),
        contentType: "application/json",

        success: function (res) {
            getAll();

            alert(res.message);

        }
    });


});

//clear BTN
$("#btn-clear1").click(function () {
    clearRow("", "", "");
});

//Data Bind with Text Fields
function bindRowClickOnAction() {
    $("#tblCustomer>tr").click(function () {

        let id = $(this).children(":eq(0)").text();
        let name = $(this).children(":eq(1)").text();
        let address = $(this).children(":eq(2)").text();

        $("#txtCustomerID").val(id);
        $("#txtCustomerName").val(name);
        $("#txtCustomerAddress").val(address);

    });

}

//Clear Text Fields
function clearRow(id, name, address) {
    $("#txtCustomerID").val(id);
    $("#txtCustomerName").val(name);
    $("#txtCustomerAddress").val(address);
}

