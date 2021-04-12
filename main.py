import plotly.express as px
import plotly as pl
import pandas as pa
import os

# This Script handles the generation of all Diagrams calculated on the basis of the activities in the BA_EPK_Simulation
# The generated Data has to be put manualy into the corresponding Files, i.e.:
# WorkingTime.txt = All Workingtimes (Workingtime.txt in the Simulation)
# DelayTime.txt = All Delaytimes (Delay.txt in the Simulation)
# CompleteTime.txt = All Completetimes (Completetime.txt in the Simulation)
# WorkingtimeFunction.txt = All Workingtimes of ONE activity (example: WorkingtimeFunction1.txt in Simulation)
# DelayTimeFunction.txt = All Delaytimes of ONE activity (example: DelaytimeFunction1.txt in Simulation)
# CompleteTimeFunction.txt = All Completetimes of ONE activity (example: CompletetimeFunction1.txt in Simulation)

# All needed Files need to be put into a Folder called Files inside this Project, so that the following script can
# find the Data for the Calculation.

# Calculates a Violin Diagram of All Workingtimes of all Activities in WorkingTime.txt
data = pa.read_csv("Files/WorkingTime.txt", sep="|", header=None)
data.columns = ["Activityname", "WorkingTime"]
fig = px.violin(data, x="Activityname", y="WorkingTime", color="Activityname", box=True, points="all", )
if not os.path.exists("images"):
    os.mkdir("images")
fig.write_image("images/WorkingTime.png")

# Calculates a Violin Diagram of All DelayTimes of all Activities in DelayTime.txt
data = pa.read_csv('Files/DelayTime.txt', sep="|", header=None)
data.columns = ["Activityname", "DelayTime"]
fig = px.violin(data, x="Activityname", y="DelayTime", color="Activityname", box=True, points="all", )
if not os.path.exists("images"):
    os.mkdir("images")
fig.write_image("images/Delay.png")

# Calculates a Violin Diagram of All CompleteTimes of all Activities in CompleteTime.txt
data = pa.read_csv('Files/CompleteTime.txt', sep="|", header=None)
data.columns = ["Activityname", "Completetime"]
fig = px.violin(data, x="Activityname", y="Completetime", color="Activityname", box=True, points="all", )
if not os.path.exists("images"):
    os.mkdir("images")
fig.write_image("images/Completetime.png")

# Calculates a Line Diagram of All WorkingTimes of One Activity in WorkingtimeFunction.txt
data = pa.read_csv('Files/WorkingtimeFunction.txt', sep="|", header=None)
data.columns = ["Sorting", "Activityname", "WorkingTime"]
fig = px.line(data, x="Sorting", y="WorkingTime", color="Activityname", )
if not os.path.exists("images"):
    os.mkdir("images")
fig.write_image("images/WorkingtimeFunction.png")

# Calculates a Line Diagram of All DelayTimes of One Activity in DelayTimeFunction.txt
data = pa.read_csv('Files/DelayTimeFunction.txt', sep="|", header=None)
data.columns = ["Sorting", "Activityname", "DelayTime"]
fig = px.line(data, x="Sorting", y="DelayTime", color="Activityname", )
if not os.path.exists("images"):
    os.mkdir("images")
fig.write_image("images/DelayTimeFunction.png")

# Calculates a Line Diagram of All CompleteTimes of One Activity in CompleteTimeFunction.txt
data = pa.read_csv('Files/CompleteTimeFunction.txt', sep="|", header=None)
data.columns = ["Sorting", "Activityname", "CompleteTime"]
fig = px.line(data, x="Sorting", y="CompleteTime", color="Activityname", )
if not os.path.exists("images"):
    os.mkdir("images")
fig.write_image("images/CompleteTimeFunction.png")
