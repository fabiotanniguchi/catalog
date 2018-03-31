@txn
Feature: Products operations

	Scenario: Recuperar Produto
		Given an existing product
		When there is a get operation for an existing resource
		Then server responds "200"
			And there is a "ETag" Response Header
			And the product was gotten
	
	Scenario: Recuperar Produto não existente
		When there is a get operation for a non existing resource
		Then server responds "404"
			
	Scenario: Recuperar Produto mesma versão
		Given an existing product
		When there is a latest-version-etagged get operation
		Then server responds "304"
			And there is a "ETag" Response Header
			
	Scenario: Recuperar Produto versão distinta
		Given an existing product
		When there is a older-version-etagged get operation
		Then server responds "200"
			And there is a "ETag" Response Header
			And the product was gotten
			
	Scenario: Atualizar Produto
		Given an existing product
		When there is an update operation for an existing entity
		Then server responds "204"
			And there is a "Location" Response Header
			And there is a "ETag" Response Header
			And product has changed
	
	Scenario: Atualizar Produto não existente
		When there is an update operation for a non existing entity
		Then server responds "404"
		
	Scenario: Atualizar Produto não existente
		When there is an inconsistent update operation
		Then server responds "400"
	
	Scenario: Listar Produto
		Given an existing product
		When there is a matching list operation by name
		Then server responds "200"
			And there is a "Page-Number" Response Header
			And the list was gotten
			And there are elements on list

	Scenario: Listar Produto - Lista Vazia
		When there is a non matching list operation by name
		Then server responds "200"
			And there is a "Page-Number" Response Header
			And the list was gotten
			And there are no elements on list

	Scenario: Cadastrar Produto
		When a product is created
		Then server responds "201"
			And there is a "Location" Response Header
			And there is a "ETag" Response Header
			
	Scenario: Cadastrar Produto com ID
		When an attempt to create a product with ID is made 
		Then server responds "400"
		
	Scenario: Listar Segunda Pagina
		Given "130" existing entities
		When there is a page "2" list operation
		Then server responds "200"
			And there is a "Previous-Page" Response Header

	Scenario: Deletar Produto
		Given an existing product
		When there is a delete operation for an existing resource
		Then server responds "204"
		
	Scenario: Deletar Produto inexistente
		When there is a delete operation for a non existing resource
		Then server responds "404"
		
	Scenario: Cancelar Delete Produto
		Given a deleted product
		When there is an undelete operation for an existing resource
		Then server responds "204"
		
	Scenario: Cancelar Delete Produto inexistente
		When there is an undelete operation for a non existing resource
		Then server responds "404"
