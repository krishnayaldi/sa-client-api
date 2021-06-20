
As mentioned in given pdf I have created below URLs to support create, update and search operation on client

1. methodType GET, URL hostname:portNo/clients gives you list of all available clients
2. methodType POST, URL hostname:portNo/client allows you to create a client with some validation rule as mentioned in given pdf
	Validation rules
		1. FirstName, LastName and IdNumber are not optional
		2. API also checks if IdNumber or mobileNumber is allready existing or not. If exists then it throws duplicasy error
		3. IdNumber follows rule provided on wiki
			a] https://en.wikipedia.org/wiki/South_African_identity_card
			b] https://en.wikipedia.org/wiki/Luhn_algorithm
		
3. methodType GET, URL hostname:portNo/clients/{input} allows user to search client by firstName or IdNumber or mobileNumber from the list of all available clients
4. methodType PUT, URL hostname:portNo/clients/{input} and it expects valid Client JSON input inorder update the existing client resource on server
	Validation rules
		1. It accepts IdNumber as path variable and Client object as RequestBody
		2. IdNumber in as input variable and IdNumber present in client object must match or else it will throw an exception
5. methodType GET, URL hostname:portNo/clients/validateSaId/{input} this URL returns true if provided input is valid sa id
	To validate SA ID I have created above API. Listing below few valid SA ids
	8612233764095, 9512084235182, 6712167234089
