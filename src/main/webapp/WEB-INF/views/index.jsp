<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Book Store</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <h2>Books Catalog</h2>

    <c:choose>
      <c:when test = "${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:when>
      <c:otherwise>
        <button class="btn btn-primary mb-2" data-toggle="modal" data-target="#addModal">Add Book</button>
      </c:otherwise>
    </c:choose>

    <!-- Table -->
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ISBN</th>
            <th>Name</th>
            <th>Book Type</th>
            <th>Published Date</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>${book.isbn}</td>
                <td>${book.name}</td>
                <td>${book.bookType}</td>
                <td>${book.publishedDate}</td>
                <td>${book.price}</td>
                <td>
                    <button class="btn btn-sm btn-warning edit-btn"
                            data-isbn="${book.isbn}"
                            data-name="${book.name}"
                            data-book="${book.bookType}"
                            data-published="${book.publishedDate}"
                            data-price="${book.price}">
                        Edit
                    </button>
                    <form action="delete/${book.isbn}" method="post" style="display:inline;">
                        <button class="btn btn-sm btn-danger" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Add Book Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <form action="add" method="post" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add Book</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                 <div class="form-group">
                    <label>ISBN:</label>
                    <input type="number" name="isbn" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label>Name:</label>
                    <input name="name" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label>Type:</label>
                    <select name="bookType" class="form-control" required>
                        <c:forEach var="bookType" items="${bookTypes}">
                            <option value="${bookType}">${bookType}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Published Date:</label>
                    <input type="date" name="publishedDate" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label>Price:</label>
                    <input type="number" name="price" class="form-control" step="0.01" min="0" required/>
                </div>

            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="submit">Save</button>
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
            </div>
        </form>
    </div>
</div>

<!-- Edit Book Modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <form action="update" method="post" class="modal-content">
            <input type="hidden" name="isbn" id="edit-isbn">
            <div class="modal-header">
                <h5 class="modal-title">Edit Book</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Name:</label>
                    <input name="name" id="edit-name" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label>Type:</label>
                    <select name="bookType" id="edit-book" class="form-control" required>
                        <c:forEach var="bookType" items="${bookTypes}">
                            <option value="${bookType}">${bookType}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Published Date:</label>
                    <input type="date" name="publishedDate" id="edit-published" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label>Price:</label>
                    <input type="number" name="price" id="edit-price" class="form-control" step="0.01" min="0" required/>
                </div>

            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="submit">Update</button>
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
            </div>
        </form>
    </div>
</div>

<script>
    $(document).on("click", ".edit-btn", function () {
        let name = $(this).data("name");
        let bookType = $(this).data("book");
        let publishedDate = $(this).data("published");
        let price = $(this).data("price");
        let isbn = $(this).data("isbn");

        $("#edit-name").val(name);
        $("#edit-bookType").val(bookType);
        $("#edit-published").val(publishedDate);
        $("#edit-price").val(price);
        $("#edit-isbn").val(isbn);
        $("#editModal").modal("show");
    });

</script>
</body>
</html>
