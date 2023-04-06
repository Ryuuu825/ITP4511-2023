GLASSFISHSERVER=~/GlassFish_Server/bin/asadmin
cwd=$(shell pwd)
# project name extracted from the directory name of the project
PROJECT=$(shell basename $(cwd))

start:
	$(GLASSFISHSERVER) start-domain
	make run

stop:
	$(GLASSFISHSERVER) stop-domain
	make clean
	
run: clean 
	ant -f $(cwd) -Dnb.internal.action.name=run -Ddirectory.deployment.supported=true -DforceRedeploy=false -Dnb.wait.for.caches=true -Dbrowser.context=$(cwd) run
	
	@echo "Running at http://localhost:8080/${PROJECT}/"

clean:
	ant -f $(cwd) -Dnb.internal.action.name=rebuild -DforceRedeploy=false -Dbrowser.context=$(cwd) clean dist

undeploy:
	$(GLASSFISHSERVER) undeploy $(PROJECT)

deploy: 
	$(GLASSFISHSERVER) deploy $(cwd)/dist/$(PROJECT).war

redeploy:
	$(GLASSFISHSERVER) redeploy --name ITP4511 $(cwd)/dist/$(PROJECT).war 

build: .FORCE
	ant -f $(cwd) -Dnb.internal.action.name=rebuild -DforceRedeploy=false -Dbrowser.context=$(cwd) dist

uc: build redeploy

.FORCE:
