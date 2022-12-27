using BabyScoller.SDK;
using System.IO;
using System.IO.IsolatedStorage;
using System.Runtime.Loader;
using System.Transactions;

var folder = Path.Combine(Environment.CurrentDirectory, "Animals");
var files = Directory.GetFiles(folder);

var animalTypes = new List<Type>();
foreach (var file in files)
{ 
    var assembly = AssemblyLoadContext.Default.LoadFromAssemblyPath(file);
    var types = assembly.GetTypes();
    foreach (var type in types)
    {
        if (type.GetInterfaces().Contains(typeof(IAnimals)))
        {
            var attri = type.GetCustomAttributes(false);
            var isUnfinished = type.GetCustomAttributes(false).Any(a => a.GetType() == typeof(UnfinishedAttribute));
            if (!isUnfinished)
            {
                animalTypes.Add(type);
            }
        }
    }
       
}

while (true)
{
    for (int i = 0; i < animalTypes.Count; i++)
    {
        Console.WriteLine($"{i+1}. {animalTypes[i].Name}");
    }

    Console.WriteLine();
    Console.WriteLine("Please choose animal.");
    int index = int.Parse(Console.ReadLine());
    if (index > animalTypes.Count || index < 1)
    {
        Console.WriteLine("No such animal,Try again!");
        continue;
    }

    Console.WriteLine("How many times?");
    int times = int.Parse(Console.ReadLine());
    var t = animalTypes[index - 1];
    var o = Activator.CreateInstance(t);
    var a = o as IAnimals;
    a.Voice(times);
}
