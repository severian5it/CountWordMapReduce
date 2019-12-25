#packages install
install.packages("ggplot2")
install.packages("ggthemes")
# library import
library(ggplot2)
library(ggthemes)

#import italian
italian <- read.table("~/alfa/italianout.txt", sep="\t")
names(italian) <- c("Letter","Frequency")
italian["source"] <- c("italian")
#import latin
latin <- read.table("~/alfa/latinout.txt", sep="\t")
names(latin) <- c("Letter","Frequency")
latin["source"] <- c("latin")
#import romanian
romanian <- read.table("~/alfa/romanianout.txt", sep="\t")
names(romanian) <- c("Letter","Frequency")
romanian["source"] <- c("romanian")

#merging three datasets
output <- rbind(italian, latin,romanian)

# BarChart definition
sp <- ggplot(output, aes(x=Letter, y=Frequency)) + 
  geom_bar(stat="identity", width= 0.8,aes(fill=Letter)) 

# BarChart visualization with faceting
sp + facet_grid(source ~ .) + 
  theme_economist() + theme(legend.position="none")+ 
  labs(title="Letter Frequency per Language", 
       caption="source: Gutenberg Project")

# LinePlot definition
p<-ggplot(output, aes(x=Letter, y=Frequency, group=source)) +
  geom_line(aes(color=source))+
  geom_point(aes(color=source))

# LinePlot Visualization
p + theme_economist() + 
  labs(title="Letter Frequency Line Plot", 
       caption="source: Gutenberg Project")

